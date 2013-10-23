'use strict';


/**
 * @constructor
 * @param {Number} options.centerLeft
 * @param {Number} options.centerTop
 * @param {Number} options.speedX Amount of pixels incrementing to bomb's position each tick.
 * @param {Number} options.speedY
 * @param {Function} options.ondie Callback running then bomb is finally out of game canvas.
 */
var Bomb = function(options){
  this.centerLeft = options.centerLeft;
  this.centerTop = options.centerTop;
  this.speedX = options.speedX || 0;
  this.speedY = options.speedY || 0;
  this.ondie = options.ondie;
  this.radius = Game.canvasWidth * Bomb.radiusFraction;
};

_.extend(Bomb.prototype, {
  tick: function(){
    this.centerLeft += this.speedX;
    this.centerTop += this.speedY;

    /* If bomb never return to canvas, call ``ondie`` callback. */
    if(this.centerTop - this.radius > Game.canvasHeight ||
       this.centerLeft + this.radius < 0 ||
       this.centerLeft - this.radius > Game.canvasWidth
      ){
      this.ondie(this);
    }else{
      this.speedY += Game.canvasHeight * Bomb.gFraction / Game.ticksPerSecond;
    }
  },

  paint: function(context){
    context.beginPath();
    context.fillStyle = '#000';
    context.arc(this.centerLeft, this.centerTop, this.radius, 0, Math.PI * 2, true);
    context.fill();
  }
});


/**
 * @constructor
 * @param {DOMElement} options.canvas
 * @param {Boolean} options.isLeftMySide Which of two towers will user own.
 */
var Game = function(options){
  options.canvas.width = Game.canvasWidth;
  options.canvas.height = Game.canvasHeight;
  
  this.canvasLeft = options.canvas.offsetLeft;
  this.canvasTop = options.canvas.offsetTop;

  var $canvas = $(options.canvas);
  $canvas.click(_.bind(this.canvasOnclick, this));
  
  this.context = options.canvas.getContext('2d')
  var towerWidth = Game.canvasWidth * Tower.widthFraction;
  var towerHeight = Game.canvasHeight * Tower.heightFraction;
  var towerMarginX = Game.canvasWidth * Tower.marginXFraction;
  var towerTop = Game.canvasHeight - towerHeight;
  
  var towerLeft = new Tower({
    left: towerMarginX,
    top: towerTop,
    width: towerWidth,
    height: towerHeight
  });
  var towerRight = new Tower({
    left: Game.canvasWidth - towerWidth - towerMarginX,
    top: towerTop,
    width: towerWidth,
    height: towerHeight,
    flipped: true
  });
  this.isLeftMySide = options.isLeftMySide;
  if(this.isLeftMySide){
    this.towerMy = towerLeft;
    this.towerEnemy = towerRight;
  }else{
    this.towerMy = towerRight;
    this.towerEnemy = towerLeft;
  }

  this.bombs = [];

  var that = this;
  setInterval(
    function(){
      that.tick();
      that.paint();
    },
    1000 / Game.ticksPerSecond
  );
};

_.extend(Game.prototype, {
  bombOndie: function(bomb){
    this.bombs = _.without(this.bombs, bomb);
  },

  canvasOnclick: function(e){
    var x = e.pageX - this.canvasLeft;
    var y = e.pageY - this.canvasTop;

    this.fire(this.towerMy.getBomb(x, y));
  },

  fire: function(bomb){
    bomb.ondie = _.bind(this.bombOndie, this);
    this.bombs.push(bomb);
  },
  
  paint: function(){
    var gradient = this.context.createLinearGradient(0, 0, 0, Game.canvasHeight);
    gradient.addColorStop(0, '#77f');
    gradient.addColorStop(0.87, '#fff');
    gradient.addColorStop(1, '#0f0');
    this.context.fillStyle = gradient;
    this.context.fillRect(0, 0, Game.canvasWidth, Game.canvasHeight);
    
    this.towerMy.paint(this.context);
    this.towerEnemy.paint(this.context);
    
    var that = this;
    _.each(this.bombs, function(bomb, i){
      bomb.paint(that.context);
    });
  },

  tick: function(){
    _.each(this.bombs, function(bomb, i){
      bomb.tick();
    });
  }
});


/**
 * @constructor
 * @param {Number} options.left
 * @param {Number} options.top
 * @param {Number} options.width
 * @param {Number} options.height
 * @param {Boolean} options.flipped Should tower be mirrored horizontally.
 */
var Tower = function(options){
  this.left = options.left;
  this.top = options.top;
  this.width = options.width;
  this.height = options.height;
  this.image = $('.tower')[0];
  this.flipped = options.flipped;
  if(this.flipped){
    var canvas = document.createElement('canvas');
    canvas.width = this.width;
    canvas.height = this.height;
    var canvasContext = canvas.getContext('2d');
    canvasContext.translate(this.width, 0);
    canvasContext.scale(-1, 1);
    canvasContext.drawImage(this.image, 0, 0, this.width, this.height)
    this.image = canvas;
  }
}

_.extend(Tower.prototype, {
  /**
   * Builds new bomb for firing from this tower.
   *
   * @this {Tower}
   * @param {Number} x Cursor coordinates on canvas used for bomb speed counting.
   * @param {Number} y
   * @return {Bomb} Bomb for firing from this tower.
   */
  getBomb: function(x, y){
    var dx = x - this.getCenterX();
    var dy = y - this.getCenterY();
    
    var hypotenuse = Math.sqrt(dx * dx + dy * dy);
    
    return new Bomb({
      centerLeft: this.getCenterX(),
      centerTop: this.getCenterY(),
      speedX: Game.canvasWidth * Bomb.speedFraction * dx / hypotenuse / Game.ticksPerSecond,
      speedY: Game.canvasHeight * Bomb.speedFraction * dy / hypotenuse / Game.ticksPerSecond
    });
  },
  
  getCenterX: function(){
    return this.left + this.width / 2;
  },

  getCenterY: function(){
    return this.top + this.height / 2;
  },
  
  paint: function(context){
    context.drawImage(this.image, this.left, this.top, this.width, this.height);
  }
});


window.Game = Game;
Game.Bomb = Bomb;
Game.Tower = Tower;
