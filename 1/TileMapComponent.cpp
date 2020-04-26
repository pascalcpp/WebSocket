#include"TileMapComponent.h"
#include"Actor.h"
TileMapComponent::TileMapComponent(class Actor* owner, int drawOrder)
	:SpriteComponent(owner,drawOrder){
	GetMatrixString();
	TransToInt(mInt, mString);
}
void inline GetRC(int& trow, int& tcolum, int& num) {
	trow = num % 24;
	tcolum = num % 8;
}

void TileMapComponent::Draw(SDL_Renderer* renderer) {
	SDL_Rect srcRect;
	srcRect.w = 32;
	srcRect.h = 32;
	SDL_Rect dstRect;
	dstRect.w = 32;
	dstRect.h = 32;
	int dstRow = mInt.size();
	
	int dstColum = mInt[0].size();
	int srcRow;
	int srcColum;
	for (int row = 0; row < dstRow; ++row) {
		for (int colum = 0; colum < dstColum; ++colum) {
			int num = mInt[row][colum];
			GetRC(srcRow, srcColum, num);
			srcRect.x = 32 * srcColum;
			srcRect.y = 32 * srcRow;

			dstRect.x = 32 * colum;
			dstRect.y = 32 * row;
			SDL_RenderCopyEx(renderer,
				mTexture,
				&srcRect,
				&dstRect,
				mOwner->GetRotation(),
				nullptr,
				SDL_FLIP_NONE);
		}
	}
}
void TileMapComponent::TransToInt(MatrixInt& _out, MatrixString& _in) {
	int nRow = _in.size();
	int nColum = _in[0].size();
	MatrixInt temp;
	for (int row = 0; row < nRow; ++row) {
		vector<int> vRow;
		for (int colum = 0; colum < nColum; ++colum) {
			int it = std::stoi(_in[row][colum]);
			vRow.emplace_back(it);
		}
		temp.emplace_back(vRow);
	}
	_out = temp;
}

void TileMapComponent::GetMatrixString() {
	CSVFile rCSV;
	const string CSVPath = "./Assets/MapLayer1.csv";

	rCSV.Init(CSVPath.c_str());
	mString = rCSV.GetMatrix();
}