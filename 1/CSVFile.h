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

	//以文件名初始化表格数据
	bool Init(const char* szFileName, char cSpliteToken = ',');
	MatrixString& GetMatrix() { return m_content; }
	//以指定分隔符从一行数据中获取关键字列表
	bool Splite(const string& strLine, VecString& vecString);

private:
	// 检测读出的数据是否合法
	bool _CheckDataValid();

protected:
	MatrixString m_content;
	char m_cSpliteToken;
};
