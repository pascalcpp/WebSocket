
#include "CSVFile.h"


CSVFile::CSVFile()
{
	m_content.clear();
}

bool CSVFile::Init(const char* szFileName, char cSpliteToken)
{
	ifstream inFile(szFileName);

	if (!inFile)
	{
		return false;
	}

	//��ȡ�ָ���
	m_cSpliteToken = cSpliteToken;

	// ÿ�ζ�ȡһ���ı�����ֱ���ļ���β
	string strLineContext;
	while (getline(inFile, strLineContext))
	{
		// ��ָ���ָ�����ȡÿ����Ϣ�б�
		VecString vecValue;
		Splite(strLineContext, vecValue);
		m_content.push_back(vecValue);
	}

	// ���������Ƿ����Ҫ��
	if (!_CheckDataValid())
	{
		return false;
	}

	return true;
}

bool CSVFile::Splite(const string& strLine, VecString& vecString)
{
	int nBegin = 0;
	int nEnd = 0;

	while ((nEnd = strLine.find_first_of(m_cSpliteToken, nBegin)) != string::npos)
	{
		vecString.push_back(strLine.substr(nBegin, nEnd - nBegin));
		nBegin = nEnd + 1;
	}

	if ((nBegin = strLine.find_last_of(m_cSpliteToken, strLine.length() - 1)) != string::npos)
	{
		vecString.push_back(strLine.substr(nBegin + 1, strLine.length() - 1));
	}

	return true;
}

bool CSVFile::_CheckDataValid()
{
	int nNumInOneLine = 0;

	MatrixString::const_iterator itRowData = m_content.begin();
	for (; itRowData != m_content.end(); itRowData++)
	{
		const VecString& curVecString = *itRowData;
		nNumInOneLine = curVecString.size();
		if (nNumInOneLine == 0)
		{
			return false;
		}
	}

	return true;
}