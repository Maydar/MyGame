var Field = new Array();  //массив поля
var NumberCells = 15; //количество элементов массива поля (размер поля)
var Number_Win = 5;   //количество элементов, к-ые необходимо собрать для выигрыша
var Nul = 1;          //означает поставленный нолик
var Xrest = -1;       //означает поставленный крестик
var sygnal_ = 0;
var PlayField = [0, 0, 0, 0]; //область, где реально происходит игра
var last_xrest_str = 0;   //Номер строки, куда поставлен последний крестик
var last_xrest_col = 0;   //Номер столбца, куда поставлен последний крестик
var First = 1;            //Первый ход
var Second = 2;           //Второй и далее ходы
var Number_var = 0;       //Номер хода (1-ый или последующий)
var CheckField = [0, 0, 0, 0];
var block = true;         //Блокирует поле (оно не реагирует на события)

function FullMas ()  //Функция инициализирует массив поля

	{	var i = 0, j = 0;
	Number_var = 1;
	block = false;
	for (i = 0; i < NumberCells; i++)

		{		Field[i] = new Array ();
		for (j = 0; j < NumberCells; j++)

			{			Field [i] [j] = 0;			}		}
	Field [7] [7] = -1;
	DrawFigure ();
	}

function TurnPlayer (event)  //Функция, отвечающая за ход игрока

	{	if (block == true) return;
	var MouseX, MouseY;
 	MouseX = event.clientX - 450;
	MouseY = event.clientY - 50;
	MouseX = MouseX / 40;
	MouseY = MouseY / 40;
	MouseX = MouseX - (MouseX % 1);
	MouseY = MouseY - (MouseY % 1);
	if (Field [MouseY] [MouseX] == 0)

		{
		Field [MouseY] [MouseX] = 1;
		last_xrest_str = MouseY;
		last_xrest_col = MouseX;
		DrawFigure ();
		CheckRezult (MouseY, MouseX);
        FillPlayField (Number_var);        //Определяем размер игрового поля
        CountPlaceXrest ();      //Ходит компьютер
        Field [last_xrest_str] [last_xrest_col] = -1;
        DrawFigure ();
        CheckRezult (last_xrest_str, last_xrest_col);
        FillPlayField (Number_var);
		}
    Number_var = 2;
    }

function DrawFigure ()  //Рисует поле

	{    var i, j = 0;
    var x_text, y_text;
    var property, str;
    str = '';
    for (i = 0; i < NumberCells; i++)

    	{    	for (j = 0; j < NumberCells; j++)

    		{    		x_text = 454 + 40*i;
    		y_text = 54 + 40*j;
    		if (Field [j] [i] == 1)

    			{
    			property = '"position:absolute; top:' + y_text + 'px; left: ' + x_text + 'px;';
    			property = property + 'z-index: 2;"';
    			str = str + '<image src = "null.jpg" alt = "Null" height = 30 width = 30 style = '
    			          + property + '> ';
    		 	}
    		if (Field [j] [i] == -1)

    			{
    			property = '"position:absolute; top:' + y_text + 'px; left: ' + x_text + 'px;';
    			property = property + 'z-index: 2;"';
    			str = str + '<image src = "xrest.jpg" alt = "Null" height = 32 width = 32 style = '
    			          + property + '> ';
    		 	}
    		}
    	}
    document.getElementById("rezult").innerHTML = str;
    }

function CheckElementByString (beginning_string, beginning_column, number_elem, type_object)
//Проверяет number_elem элементов, стоящих подряд на строке, начало находится
//в положении beginning_string, beginning_column
//type_object определяет, что проверяем: крестики или нолики
//Param определяет режим проверки

{
    var i = beginning_string, j = beginning_column, count = 0, check = 1;
    if (j < NumberCells - number_elem + 1) //Проверяем строку

    {
       for (count = 0; count < number_elem; count++)

           {
           if (Field [i] [j] == type_object) j++;
           else check = 0;
           }

	   if (check == 1) { control = 1; return 1; }
    }
    return 0;
}

function CheckElementByColumn (beginning_string, beginning_column, number_elem, type_object)
//Проверяет number_elem элементов, стоящих подряд по вертикали, начало находится
// в положении beginning_string, beginning_column
//type_object определяет, что проверяем: крестики или нолики
//Param определяет режим проверки ????????

{
    var i = beginning_string, j = beginning_column, count = 0, check = 1;
    if (i < NumberCells - number_elem + 1) //Проверяем столбец

    {
       for (count = 0; count < number_elem; count++)

           {
           if (Field [i] [j] == type_object) i++;
           else check = 0;
           }
	   if (check == 1) {control = 2; return 1; }
    }
    return 0;
}

function CheckElementByRightDiagonal (beginning_string, beginning_column, number_elem, type_object)
//Проверяет number_elem элементов, стоящих подряд на правой диагонали, начало находится
// в положении beginning_string, beginning_column
//type_object определяет, что проверяем: крестики или нолики
//Param определяет режим проверки

{
    var i = beginning_string, j = beginning_column, count = 0, check = 1;
    if (j < NumberCells - number_elem + 1 && i < NumberCells - number_elem + 1)

    {
       for (count = 0; count < number_elem; count++)

           {
           if (Field [i] [j] == type_object) j++, i++;
           else check = 0;
           }

	   if (check == 1) { control = 3; return 1; }
    }
    return 0;
}

function CheckElementByLeftDiagonal (beginning_string, beginning_column, number_elem, type_object)
//Проверяет number_elem элементов, стоящих подряд на левой диагонали, начало находится
// в положении beginning_string, beginning_column
//type_object определяет, что проверяем: крестики или нолики
//Param определяет режим проверки

{
    var i = beginning_string, j = beginning_column, count = 0, check = 1;
    if (i < NumberCells - number_elem + 1 && j >= number_elem - 1)

    {
       for (count = 0; count < number_elem; count++)

           {
           if (Field [i] [j] == type_object) i++, j--;
           else check = 0;
           }

	   if (check == 1) {control = 4; return 1; }
    }
    return 0;
}

function CheckAllsElement (number_elem, type_object, last_mot_str, last_mot_col, action)
//Возвращает количество number_elem элементов, стоящих подряд на столбце/строке/диагонали
//type_object определяет, что проверяем: фишки компьютера или игрока
//last_mot_str - номер строки, куда был поставлен последний символ
//last_mot_col - номер столбца, куда был поставлен последний символ
//action - определяет, идет проверка, кто выиграл или же функция используется для комбинации

{
    var i = 0, j = 0, sum = 0, znak = 0;
    //sum - количество встретившихся комбинаций
    //проверяем все элементы в строке
    i = last_mot_str;
    for (j = 0; j < NumberCells - 1; j++)

		{
		sum += CheckElementByString (i, j, number_elem, type_object);
        if (action == true && sum == 1 && znak == 0) { znak = 1; }
		}

	if (sygnal_ == 0 && sum == 1) sygnal_ = 1;
    //проверяем все элементы в столбце
    j = last_mot_col;
    for (i = 0; i < NumberCells - 1; i++)

		{		sum += CheckElementByColumn (i, j, number_elem, type_object);
  		if (action == true && sum == 1 && znak == 0) znak = 1;

		}

	if (sygnal_ == 0 && sum == 1) sygnal_ = 2;

    //проверяем все элементы вдоль правой диагонали
    //сначала устанавливаем начальную позицию, откуда проверять
    if (last_mot_str >= last_mot_col) i = last_mot_str - last_mot_col, j = 0;
    else j = last_mot_col - last_mot_str, i = 0;
    for (; i < NumberCells - 1 && j < NumberCells - 1; i++, j++)
		{		sum += CheckElementByRightDiagonal (i, j, number_elem, type_object);
    	if (action == true && sum == 1 && znak == 0) znak = 1;
    	}

    if (sygnal_ == 0 && sum == 1) sygnal_ = 3;

    //проверяем все элементы вдоль левой диагонали
    //сначала устанавливаем начальную позицию, откуда проверять
    if (last_mot_str >= NumberCells - last_mot_col-1) i = last_mot_str + last_mot_col + 1 - NumberCells, j = NumberCells-1;
    else j = last_mot_col + last_mot_str, i = 0;
    for (; i < NumberCells - 1 && j >= 0; i++, j--)
		{		sum += CheckElementByLeftDiagonal (i, j, number_elem, type_object);
    	if (action == true && sum == 1 && znak == 0) znak = 1;
    	}

	if (sygnal_ == 0 && sum == 1) sygnal_ = 4;
    return sum*type_object;
}

//------------------------------------------------------------------------------------------------------------------------
function CheckElemWithEmpByString (beginning_string, beginning_column, number_elem, type_object)
//Проверяет number_elem - 2 элементов, стоящих подряд на строке, ограниченных 2 пустыми клетками
//начало находится в положении beginning_string, beginning_column
//type_object определяет тип объекта

{
    var i = beginning_string, j = beginning_column, count = 0, per = 0;
    //count - счетчик числа элементов (нужен, чтобы отсчитать number_elem - 2 элементов)
    //i, j определяют положение текущей проверяемой клетки
    //per определяет, есть ли слева пустая клетка
    if (j > 0) { if (Field [i] [j-1] == 0) per = 1; }
    if (j < NumberCells - number_elem + 1) //Проверяем строку

    {
       for (count = 0; count < number_elem; count++)

           {
           if ((count == 0 || count == number_elem - 1) && Field [i] [j] == 0) j++;
           if (count != 0 && count != number_elem - 1 && Field [i] [j] == type_object) j++;
           }

       if (j < NumberCells - 1) { if (Field [i] [j+1] == 0) per = 1; }
       if (j == beginning_column + number_elem &&
       (number_elem == Number_Win + 1 || per == 1)) return 1;
    }
    return 0;
}

function CheckElemWithEmpByColumn (beginning_string, beginning_column, number_elem, type_object)
//Проверяет number_elem - 2 элементов, стоящих подряд на столбце, ограниченных 2 пустыми клетками,
//начало находится в положении beginning_string, beginning_column
//type_object определяет тип объекта

{
    var i = beginning_string, j = beginning_column, count = 0, per = 0;
    //count - счетчик числа элементов (нужен, чтобы отсчитать number_elem - 2 элементов)
    //i, j определяют положение текущей проверяемой клетки
    //per определяет, есть ли слева пустая клетка
    if (i > 0) { if (Field [i-1] [j] == 0) per = 1; }
    if (i < NumberCells - number_elem + 1) //Проверяем столбец

    {
       for (count = 0; count < number_elem; count++)

           {
           if ((count == 0 || count == number_elem - 1) && Field [i] [j] == 0) i++;
           if (count != 0 && count != number_elem - 1 && Field [i] [j] == type_object) i++;
           //else check = 0;
           }
       //alert ('per = ' + per);
       if (i < NumberCells - 1) { if (Field [i+1] [j] == 0) per = 1; }
       if (i == beginning_string + number_elem &&
        (number_elem == Number_Win + 1 || per == 1)) return 1;
    }
    return 0;
}

function CheckElemWithEmpByRightDiagonal (beginning_string, beginning_column, number_elem, type_object)
//Проверяет number_elem элементов, стоящих подряд на правой диагонали, ограниченных 2 пустыми клетками,
// начало находится в положении beginning_string, beginning_column
//type_object определяет тип объекта

{
    var i = beginning_string, j = beginning_column, count = 0, per = 0;
    //count - счетчик числа элементов (нужен, чтобы отсчитать number_elem - 2 элементов)
    //i, j определяют положение текущей проверяемой клетки
    //per определяет, есть ли слева пустая клетка
    if (i > 0 && j > 0) { if (Field [i-1] [j-1] == 0) per = 1; }
    if (j < NumberCells - number_elem + 1 && i < NumberCells - number_elem + 1)

    {
       for (count = 0; count < number_elem; count++)

           {
           if ((count == 0 || count == number_elem - 1) && Field [i] [j] == 0) j++, i++;
           if (count != 0 && count != number_elem - 1 && Field [i] [j] == type_object) j++, i++;
           //else check = 0;
           }
       if (i < NumberCells - 1 && j < NumberCells - 1) { if (Field [i+1] [j+1] == 0) per = 1; }
       if (j == beginning_column + number_elem &&
        (number_elem == Number_Win + 1 || per == 1)) return 1;
    }
    return 0;
}

function CheckElemWithEmpByLeftDiagonal (beginning_string, beginning_column, number_elem, type_object)
//Проверяет number_elem элементов, стоящих подряд на левой диагонали, ограниченных 2 пустыми клетками,
//начало находится в положении beginning_string, beginning_column
//type_object определяет тип объекта

{
    var i = beginning_string, j = beginning_column, count = 0, per = 1;
    //count - счетчик числа элементов (нужен, чтобы отсчитать number_elem - 2 элементов)
    //i, j определяют положение текущей проверяемой клетки
    //per определяет, есть ли слева пустая клетка
    if (i > 0 && j < NumberCells - 1) { if (Field [i-1] [j+1] == 0) per = 1; }
    if (i < NumberCells - number_elem + 1 && j > number_elem - 1)

    {
       for (count = 0; count < number_elem; count++)

           {
           if ((count == 0 || count == number_elem - 1) && Field [i] [j] == 0) i++, j--;
           if (count != 0 && count != number_elem - 1 && Field [i] [j] == type_object) i++, j--;
           }

       if (i < NumberCells - 1 && j > 0) { if (Field [i+1] [j-1] == 0) per = 1; }
       if (i == beginning_string + number_elem &&
        (number_elem == Number_Win + 1 || per == 1)) return 1;
    }
    return 0;
}

function CheckAlWithEmpEndElem (number_elem, type_object, last_mot_str, last_mot_col)
//Возвращает количество number_elem - 2 элементов, стоящих подряд на столбце/строке/диагонали,
//ограниченных двумя пустыми клетками
//type_object определяет, что проверяем: фишки компьютера или игрока
//last_mot_str - номер строки, куда была поставлена последняя фишка
//last_mot_col - номер столбца, куда была поставлена последняя фишка

{
    var i = 0, j = 0, sum = 0;
    //i, j - текущая проверяемая клетка; sum - кол-во элементов, стоящих подряд на столбце/строке/диагонали,
	//ограниченных двумя пустыми клетками
    //Проверяем строку
    i = last_mot_str;
    for (j = 0; j < NumberCells - 1; j++)
        sum += CheckElemWithEmpByString (i, j, number_elem, type_object);

    //if (sygnal_ == 0 && sum == 1) sygnal_ = 1;

    //Проверяем столбец
    j = last_mot_col;
    for (i = 0; i < NumberCells - 1; i++)
        sum += CheckElemWithEmpByColumn (i, j, number_elem, type_object);

    //if (sygnal_ == 0 && sum == 1) sygnal_ = 2;

    //Проверяем правую диагональ
    if (last_mot_str >= last_mot_col) i = last_mot_str - last_mot_col, j = 0;
    else j = last_mot_col - last_mot_str, i = 0;

    for (; i < NumberCells - 1 && j < NumberCells - 1; i++, j++)
        sum += CheckElemWithEmpByRightDiagonal (i, j, number_elem, type_object);

    //if (sygnal_ == 0 && sum == 1) sygnal_ = 3;

    //Проверяем левую диагональ
    if (last_mot_str >= NumberCells - last_mot_col-1) i = last_mot_str + last_mot_col + 1 - NumberCells, j = NumberCells-1;
       else j = last_mot_col + last_mot_str, i = 0;

    for (; i < NumberCells - 1 && j > 0; i++, j--)
        sum += CheckElemWithEmpByLeftDiagonal (i, j, number_elem, type_object);
    //if (sygnal_ == 0 && sum == 1) sygnal_ = 4;

    return sum*type_object;
}
//-----------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------
function CheckElemFourWithEmpByString (beginning_string, beginning_column, type_object)
//Проверяет все комбинации вида _00_0_, либо _0_00_ вдоль строки
//начало находится в положении beginning_string, beginning_column
//type_object определяет, что проверяем: фишки компьютера или игрока

{
    var i = beginning_string, j = beginning_column, count = 0;
    if (j < NumberCells - 6 + 1) //Проверяем строку

    {
       if (Field [i] [j] == 0 && Field [i] [j+1] == type_object &&
           Field [i] [j+4] == type_object && Field [i] [j+5] == 0) count = 1;
       if (((Field [i] [j+2] == type_object && Field [i] [j+3] == 0) ||
       (Field [i] [j+3] == type_object && Field [i] [j+2] == 0)) && count == 1) { return 1; }
    }
    return 0;
}

function CheckElemFourWithEmpByColumn (beginning_string, beginning_column, type_object)
//Проверяет все комбинации вида _00_0_, либо _0_00_ вдоль столбца
//начало находится в положении beginning_string, beginning_column
//type_object определяет, что проверяем: фишки компьютера или игрока

{
    var i = beginning_string, j = beginning_column, count = 0;
    if (i < NumberCells - 6 + 1) //Проверяем столбец

    	{
        if (Field [i] [j] == 0 && Field [i+1] [j] == type_object &&
           Field [i+4] [j] == type_object && Field [i+5] [j] == 0) count = 1;
        if (((Field [i+2] [j] == type_object && Field [i+3] [j] == 0) ||
           (Field [i+3] [j] == type_object && Field [i+2] [j] == 0)) && count == 1) { return 1; }
    	}
    return 0;
}

function CheckElemFourWithEmpByRightDiagonal (beginning_string, beginning_column, type_object)
//Проверяет все комбинации вида _00_0_, либо _0_00_ вдоль правой диагонали
//начало находится в положении beginning_string, beginning_column
//type_object определяет, что проверяем: фишки компьютера или игрока

{
    var i = beginning_string, j = beginning_column, count = 0;
    if (j < NumberCells - 6 + 1 && i < NumberCells - 6 + 1)

    {
       if (Field [i] [j] == 0 && Field [i+1] [j+1] == type_object &&
           Field [i+4] [j+4] == type_object && Field [i+5] [j+5] == 0) count = 1;
       if (((Field [i+2] [j+2] == type_object && Field [i+3] [j+3] == 0) ||
       (Field [i+3] [j+3] == type_object && Field [i+2] [j+2] == 0)) && count == 1) { return 1; }
    }
    return 0;
}

function CheckElemFourWithEmpByLeftDiagonal (beginning_string, beginning_column, type_object)
//Проверяет все комбинации вида _00_0_, либо _0_00_ вдоль левой диагонали
//начало находится в положении beginning_string, beginning_column
//type_object определяет, что проверяем: фишки компьютера или игрока

{
    var i = beginning_string, j = beginning_column, count = 0;
	if (i < NumberCells - 6 + 1 && j >= 6 - 1)

    {
       if (Field [i] [j] == 0 && Field [i+1] [j-1] == type_object &&
           Field [i+4] [j-4] == type_object && Field [i+5] [j-5] == 0) count = 1;
       if (((Field [i+2] [j-2] == type_object && Field [i+3] [j-3] == 0) ||
       (Field [i+3] [j-3] == type_object && Field [i+2] [j-2] == 0)) && count == 1) { return 1; }
 	}
    return 0;
}

function CheckAlFourWithEmpEndElem (type_object, last_mot_str, last_mot_col)
//Возвращает количество элементов, удовлетворяющих комбинациям, описанным выше
//type_object определяет, что проверяем: фишки компьютера или игрока
//last_mot_str - номер строки, куда была поставлена последняя фишка
//last_mot_col - номер столбца, куда была поставлена последняя фишка

{
    var i = 0, j = 0, sum = 0;
    //i, j - текущая проверяемая клетка; sum - кол-во элементов, стоящих подряд на столбце/строке/диагонали,
	//ограниченных двумя пустыми клетками
    //Проверяем строку
    i = last_mot_str;
    for (j = 0; j < NumberCells - 1; j++)
        sum += CheckElemFourWithEmpByString (i, j, type_object);

    //Проверяем столбец
    j = last_mot_col;
    for (i = 0; i < NumberCells - 1; i++)
        sum += CheckElemFourWithEmpByColumn (i, j, type_object);

    //Проверяем правую диагональ
    if (last_mot_str >= last_mot_col) i = last_mot_str - last_mot_col, j = 0;
    else j = last_mot_col - last_mot_str, i = 0;

    for (; i < NumberCells - 1 && j < NumberCells - 1; i++, j++)
        sum += CheckElemFourWithEmpByRightDiagonal (i, j, type_object);

    //Проверяем левую диагональ
    if (last_mot_str >= NumberCells - last_mot_col-1) i = last_mot_str + last_mot_col + 1 - NumberCells, j = NumberCells-1;
       else j = last_mot_col + last_mot_str, i = 0;

    for (; i < NumberCells - 1 && j > 0; i++, j--)
        sum += CheckElemFourWithEmpByLeftDiagonal (i, j, type_object);

    return sum*type_object;
}
//------------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------------
function CheckElemGranByString (beginning_string, beginning_column, number_elem, type_object)
//Проверяет number_elem - 1 элементов, стоящих подряд на строке, ограниченных 1 другим элементом,
//начало находится в положении beginning_string, beginning_column

{
    var i = beginning_string, j = beginning_column, count = 0, perem = 0;
    if (j < NumberCells - number_elem + 1) //Проверяем строку

    {
       for (count = 0; count < number_elem; count++)

           {
           if (count == 0 && Field [i] [j] == type_object && j > 0) { if (Field[i] [j-1] == 0) j++; }
           if (count == 0 && Field [i] [j] == -type_object && j == beginning_column) j++, perem = 1;
           if (count != 0 && count != number_elem - 1 && Field [i] [j] == type_object) j++;

           if (count == number_elem - 1 && ((Field [i] [j] == type_object && perem == 1) ||
              (Field [i] [j] == -type_object && perem == 0))) j++;
           }
       if (j < NumberCells)
       {
       if (j == beginning_column + number_elem && Field [i] [j] == 0 && perem == 1) return 1;
       }

       if (j == beginning_column + number_elem && perem == 0) return 1;
    }
    return 0;
}

function CheckElemGranByColumn (beginning_string, beginning_column, number_elem, type_object)
//Проверяет number_elem - 1 элементов, стоящих подряд на столбце, ограниченных 1 другим элементом,
//начало находится в положении beginning_string, beginning_column

{
    var i = beginning_string, j = beginning_column, count = 0, perem = 0;
    if (i < NumberCells - number_elem + 1) //Проверяем столбец

    {
       for (count = 0; count < number_elem; count++)

           {
           if (count == 0 && Field [i] [j] == type_object && i > 0 && Field[i-1] [j] == 0) i++;
           if (count == 0 && Field [i] [j] == -type_object && i == beginning_string) i++, perem = 1;
           if (count != 0 && count != number_elem - 1 && Field [i] [j] == type_object) i++;

           if (count == number_elem - 1 && ((Field [i] [j] == type_object && perem == 1) ||
              (Field [i] [j] == -type_object && perem == 0))) i++;
           }
       if (i < NumberCells)
       {
       if (i == beginning_string + number_elem && Field [i] [j] == 0 && perem == 1) return 1;
       }

       if (i == beginning_string + number_elem && perem == 0) return 1;
    }
    return 0;
}

function CheckElemGranByRightDiagonal (beginning_string, beginning_column, number_elem, type_object)
//Проверяет number_elem - 1 элементов, стоящих подряд на правой диагонали, ограниченных 1 другим элементом,
//начало находится в положении beginning_string, beginning_column

{
    var i = beginning_string, j = beginning_column, count = 0, perem = 0;
    if (j < NumberCells - number_elem + 1 && i < NumberCells - number_elem + 1)

    {
       for (count = 0; count < number_elem; count++)

           {
           if (count == 0 && Field [i] [j] == type_object && j > 0 && i > 0 && Field[i-1] [j-1] == 0) j++, i++;
           if (count == 0 && Field [i] [j] == -type_object && j == beginning_column) j++, i++, perem = 1;
           if (count != 0 && count != number_elem - 1 && Field [i] [j] == type_object) j++, i++;

           if (count == number_elem - 1 && ((Field [i] [j] == type_object && perem == 1) ||
              (Field [i] [j] == -type_object && perem == 0))) j++, i++;
           }
       if (i < NumberCells && j < NumberCells)
       {
       if (j == beginning_column + number_elem && Field [i] [j] == 0 && perem == 1) return 1;
       }

       if (j == beginning_column + number_elem && perem == 0) return 1;
    }
    return 0;
}

function CheckElemGranByLeftDiagonal (beginning_string, beginning_column, number_elem, type_object)
//Проверяет number_elem - 1 элементов, стоящих подряд на левой диагонали, ограниченных 1 другим элементом,
//начало находится в положении beginning_string, beginning_column

{
    var i = beginning_string, j = beginning_column, count = 0, perem = 0;
    if (i < NumberCells - number_elem + 1 && j > number_elem - 1)

    {
       for (count = 0; count < number_elem; count++)

           {
           if (count == 0 && Field [i] [j] == type_object && i > 0 && Field[i-1] [j+1] == 0) i++, j--;
           if (count == 0 && Field [i] [j] == -type_object && i == beginning_string) i++, j--, perem = 1;
           if (count != 0 && count != number_elem - 1 && Field [i] [j] == type_object) i++, j--;

           if (count == number_elem - 1 && ((Field [i] [j] == type_object && perem == 1) ||
              (Field [i] [j] == -type_object && perem == 0))) i++, j--;
           }
       if (i < NumberCells && j >= 0)
       {
       if (i == beginning_string + number_elem && Field [i] [j] == 0 && perem == 1) return 1;
       }

       if (i == beginning_string + number_elem && perem == 0) return 1;
    }
    return 0;
}

function CheckAllElemGran (number_elem, type_object, last_mot_str, last_mot_col)
//Возвращает количество number_elem - 1 элементов в соответствии с комбинацией, описанной выше

{
    var i = 0, j = 0, sum = 0;
    i = last_mot_str;
    for (j = 0; j < NumberCells - 1; j++)
        sum += CheckElemGranByString (i, j, number_elem, type_object);

    j = last_mot_col;
    for (i = 0; i < NumberCells - 1; i++)
        sum += CheckElemGranByColumn (i, j, number_elem, type_object);

    if (last_mot_str >= last_mot_col) i = last_mot_str - last_mot_col, j = 0;
    else j = last_mot_col - last_mot_str, i = 0;

    for (; i < NumberCells - 1 && j < NumberCells - 1; i++, j++)
        sum += CheckElemGranByRightDiagonal (i, j, number_elem, type_object);

    if (last_mot_str >= NumberCells - last_mot_col-1) i = last_mot_str + last_mot_col + 1 - NumberCells, j = NumberCells-1;
       else j = last_mot_col + last_mot_str, i = 0;

    for (; i < NumberCells - 1 && j > 0; i++, j--)
        sum += CheckElemGranByLeftDiagonal (i, j, number_elem, type_object);

    return sum*type_object;
}
//--------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------
function CheckRezult (last_mot_str, last_mot_col)

//Проверяет, привел ли последний ход к выигрышу (собирается 5 штук)
//last_mot_str - строка, в которой находится последняя поставленная фишка
//last_mot_col - столбец, в котором находится последняя поставленная фишка
//state_mot - ход, 0 - игрок, 1 - компьютер. У нас state_mot = 1

{
    //выиграл игрок
    var str = 0;
    if (CheckAllsElement (Number_Win, Nul, last_mot_str, last_mot_col, true) > 0)

    	{
        str = '<h1 style = "position: absolute; top: 500px; left: 50px; color: olive;';
        str = str + 'font-family: verdana; font-style: italic;" > Вы выиграли </h1>';
        document.getElementById ("final").innerHTML = str;
        block = true;
        return 1;
		}

    //выиграл компьютер
    if (CheckAllsElement (Number_Win, Xrest, last_mot_str, last_mot_col, true) < 0)

    	{
        str = '<h1 style = "position: absolute; top: 500px; left: 50px; color: olive;';
        str = str + 'font-family: verdana; font-style: italic;" > Вы проиграли </h1>';
        document.getElementById ("final").innerHTML = str;
        block = true;
        return -1;
    	}
return 0;
}
//----------------------------------------------------------------------------------------------------------------------
function FillPlayField (Param)

//Определяет область поля, где реально происходит игра
{
     if (last_xrest_str <= PlayField[0] || Param == First) PlayField[0] = last_xrest_str - 1;
     if (last_xrest_str >= PlayField[1] || Param == First) PlayField[1] = last_xrest_str + 1;
     if (last_xrest_col <= PlayField[2] || Param == First) PlayField[2] = last_xrest_col - 1;
     if (last_xrest_col >= PlayField[3] || Param == First) PlayField[3] = last_xrest_col + 1;

     var i = 0;
     for (i = 0; i < 4; i++)

     {
         if (PlayField[i] < 0) PlayField[i] = 0;
         if (PlayField[i] > NumberCells - 1) PlayField[i] = NumberCells - 1;
     }
	FillCheckField ();
}

function FillCheckField ()

//Определяет область, где ведется проверка
{
     if (PlayField[0] != 0) CheckField[0] = PlayField[0] - 1;
        else CheckField[0] = 0;
     if (PlayField[1] != NumberCells - 1) CheckField[1] = PlayField[1] + 1;
        else CheckField[1] = NumberCells - 1;
     if (PlayField[2] != 0) CheckField[2] = PlayField[2] - 1;
        else CheckField[2] = 0;
     if (PlayField[3] != NumberCells - 1) CheckField[3] = PlayField[3] + 1;
        else CheckField[3] = NumberCells - 1;
}

function CheckOneMove (type_object, Param, last_mot_str, last_mot_col)

//Проверяет поле на 1 ход.
//type_object - тип последней поставленной игровой фишки
//Param - определяет режим работы функции
//Param = 0 - обычный режим работы
//last_mot_str - строка, на которой был сделан последний ход
//last_mot_col - столбец, на котором был сделан последний ход

{
    var count = 0, i = CheckField[0], j = CheckField[2];
    FillCheckField ();
    //if (CheckAlWithEmpEndElem (Number_Win+1, Nul, last_mot_str, last_mot_col) == type_object && Param == 2) count-=2;
	for (i = CheckField[0]; i <= CheckField[1]; i++)

    {
         for (j = CheckField[2]; j <= CheckField[3]; j++)

         {
             if (Field [i] [j] == 0)

             {
                Field [i] [j] = type_object;

                if (CheckAllsElement (Number_Win, type_object, i, j, false) != 0)
                    {
                       Field [i] [j] = 0;
                       if (Param == 0) { last_xrest_str = i; last_xrest_col = j; Field [i] [j] = -1; return 1;}
					   //if (Param != 2) { control = 0; return 1; }
                       //if (Param == 2 || Param == 3) count++;
					}

                Field [i] [j] = 0;
             }
         }
    }
	//if (Param == 2) { control = 0; return count*type_object; }
    //control = 0;
	return 0;
}


function CountPlaceXrest ()

//Вычисляет положение крестика на экране
	{	var i = 0, j = 0, sum_max = 0, i_max = 0, j_max = 0;
	var sum_xr = 0, sum_nul = 0, sum_nul_3 = 0, sum_xr_3 = 0;
	//sum - показывает приоритет каждой ячейки
	//sum_max - максимальный приоритет среди всех ячеек
	//i_max, j_max - номер строки и столбца, приоритет которых максимален
    //sum_nul, sum_xr - приоритеты от защиты и атаки соответственно (для двойных комбинаций)
	//sum_nul_3, sum_xr_3 - приоритеты от защиты и атаки для одинарных комбинаций

	for (i = CheckField[0]+1; i <= CheckField[1]; i++)

     	{
        for (j = CheckField[2]+1; j <= CheckField[3]; j++)

             {
             if (CheckOneMove (Xrest, 0, 0, 0) == 1) return; //Проверка 4-х подряд идущих фишек
             if (CheckOneMove (Nul, 0, 0, 0) == 1) return;

			 sum = 0;
			 sum_xr = 0; sum_xr_3 = 0;
			 sum_nul = 0; sum_nul_3 = 0;
			 if (Field [i] [j] == 0)
			 	{			 	Field [i] [j] = -1;
                //Поставили крестик, проверили, какие комбинации с крестиками получились
				sum_xr_3 -= CheckAlWithEmpEndElem (6, Xrest, i, j)*30;
				sum_xr -= CheckAlWithEmpEndElem (5, Xrest, i, j)*1.4;
				sum_xr -= CheckAlFourWithEmpEndElem (Xrest, i, j)*1.2;
				sum_xr -= CheckAllElemGran (5, Xrest, i, j)*2;
				if (sum_xr > 2.1) sum_xr = sum_xr * 3; //Если была двойная комбинация, то увеличиваем приоритет

			 	//Поставили нолик, проверили, какие комбинации с ноликами получились
			 	Field [i] [j] = 1;
			 	sum_nul_3 += CheckAlWithEmpEndElem (6, Nul, i, j)*23;
				sum_nul += CheckAlWithEmpEndElem (5, Nul, i, j)*1.2;
				sum_nul += CheckAlFourWithEmpEndElem (Nul, i, j);
				sum_nul += CheckAllElemGran (5, Nul, i, j)*1.1;
				if (sum_nul > 1.9) sum_nul = sum_nul * 3; //Если была двойная комбинация, то увеличиваем приоритет

			 	Field [i] [j] = 0;
			 	sum = sum_xr + sum_nul + sum_xr_3 + sum_nul_3;
			 	if (sum > sum_max) { sum_max = sum; i_max = i; j_max = j; }     			}
             }
     	}

	//alert ('fgfgg');
	if (sum_max > 0) { last_xrest_str = i_max; last_xrest_col = j_max; return; }

	for (i = PlayField[0] + 1; i <= PlayField[1]; i++)

     	{
        for (j = PlayField[2] + 1; j <= PlayField[3]; j++)

             {
             if (Field [i] [j] == 0) { last_xrest_str = i; last_xrest_col = j; return; }
             }
     	}	alert ('Ничья');
	window.location.reload ();  //Обновление страницы
    FullMas ();
	}

