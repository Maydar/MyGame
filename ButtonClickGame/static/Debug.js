Field = new Array(); //������ ����
var NumberCells = 15; //���������� ��������� ������� ���� (������ ����)
var Number_Win = 5; //���������� ���������, �-�� ���������� ������� ��� ��������

function FullMas ()  //������� �������������� ������ ����

	{
	var i = 0, j = 0;
	for (i = 0; i < NumberCells; i++

		{
		Field[i] = new Array ();
		for (j = 0; j < NumberCells; j++)

			{
			Field [i] [j] = 0;
			}
		}
	}