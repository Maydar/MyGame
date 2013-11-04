mw.loader.implement("ext.vector.footerCleanup",function(){(function($){$.fn.footerCollapsibleList=function(config){if(!('title'in config)||!('name'in config)){return;}return this.each(function(){var $container,$ul,$explanation,$icon;$container=$(this);$ul=$container.find('ul');$explanation=$container.find('.mw-templatesUsedExplanation, .mw-hiddenCategoriesExplanation');$icon=$('<span>');$ul.before($('<a>').addClass('collapsible-list').text(config.title).append($icon).on('click',function(e){var state=($.cookie(config.name)!=='expanded')?'expanded':'collapsed';$.cookie(config.name,state);$ul.slideToggle();$icon.toggleClass('collapsed');e.preventDefault();}));$explanation.remove();if($.cookie(config.name)===null||$.cookie(config.name)==='collapsed'){$ul.hide();$icon.addClass('collapsed');}});};}(jQuery));(function($){window.onload=function(){if('wikiEditor'in $){$('.editButtons').find('.editHelp').remove();var $cancelLink=$('#mw-editform-cancel');$cancelLink.parent().empty().append(
$cancelLink);$('.editOptions, #editpage-specialchars').css('margin-right','-2px');}};$(document).ready(function(){$('.templatesUsed').footerCollapsibleList({name:'templates-used-list',title:mw.msg('vector-footercleanup-templates')});$('.hiddencats').footerCollapsibleList({name:'hidden-categories-list',title:mw.msg('vector-footercleanup-categories')});});}(jQuery));;},{"css":[
"#wpTextbox1{margin:0;display:block}.editOptions{background-color:#F0F0F0;border:1px solid silver;border-top:none;padding:1em 1em 1.5em 1em;margin-bottom:2em} .collapsible-list{display:inline;cursor:pointer;min-width:400px}.collapsible-list span{float:left;background-image:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQBAMAAADt3eJSAAAAD1BMVEX////d3d2ampqxsbF5eXmCtCYvAAAAAXRSTlMAQObYZgAAADBJREFUeF6dzNEJACAMA1HdINQJCp1Ebv+ZlLYLaD4f4cbnDNi6MAO8KCHJ+7X02j3mzgMQe93HcQAAAABJRU5ErkJggg==);background-image:url(//bits.wikimedia.org/static-1.21wmf11/extensions/Vector/modules/./images/open.png?2013-03-04T18:38:20Z)!ie;background-repeat:no-repeat;background-position:50% 50%;display:block;height:16px;width:16px}.collapsible-list span.collapsed{background-image:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQAgMAAABinRfyAAAADFBMVEX///95eXnd3d2dnZ3aAo3QAAAAAXRSTlMAQObYZgAAADFJREFUeF5dyzEKACAMA0CXolNe2Id09Kl5igZahWY4AiGjZwmIuS9GEcJfY63Ix88Bol4EYP1O7JMAAAAASUVORK5CYII=);background-image:url(//bits.wikimedia.org/static-1.21wmf11/extensions/Vector/modules/./images/closed-ltr.png?2013-03-04T18:38:20Z)!ie}.hiddencats ul,.templatesUsed ul{margin-bottom:1em;margin-left:2.5em} .editCheckboxes{margin-bottom:1em}.editCheckboxes input:first-child{margin-left:0}.cancelLink{margin-left:0.5em}#editpage-copywarn{font-size:0.9em}#wpSummary{display:block;margin-top:0;margin-bottom:0.5em}.editButtons input:first-child{margin-left:.1em}\n/* cache key: enwiki:resourceloader:filter:minify-css:7:3fa2e027b7bd2af1f98df5a7a2309b0c */"
]},{"vector-footercleanup-transclusion":"This page contains {{PLURAL:$1|transclusion|transclusions}} of {{PLURAL:$1|one other page|$1 other pages}}.","vector-footercleanup-templates":"View templates on this page","vector-footercleanup-categories":"View hidden categories on this page"});
/* cache key: enwiki:resourceloader:filter:minify-js:7:5017f0807de513e323ae852f8788968c */