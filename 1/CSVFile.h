#pragma once

#include <fstream>
#include <vector>
#include <string>
#include <iostream>

using namespace std;

typedef vector< string > VecString;
typedef vector< VecString > MatrixString;

class CSVFile
{
public:
	CSVFile();

	//���ļ�����ʼ���������
	bool Init(const char* szFileName, char cSpliteToken = ',');
	MatrixString& GetMatrix() { return m_content; }
	//��ָ���ָ�����һ�������л�ȡ�ؼ����б�
	bool Splite(const string& strLine, VecString& vecString);

private:
	// �������������Ƿ�Ϸ�
	bool _CheckDataValid();

protected:
	MatrixString m_content;
	char m_cSpliteToken;
};
