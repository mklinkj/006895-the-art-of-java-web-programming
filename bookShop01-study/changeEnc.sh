#!/bin/bash
# 프로젝트 소스 경로 지정
DIR="./src"

# 특정 경로의 모든 파일 조회
for file in $(find $DIR); do
    echo $file
    # 파일이면
    if [ -f "$file" ]; then
        # 파일 확장자 검사...
        EXTENSION="${file##*.}"
        # 확장자가 .java, .xml, .jsp, .properties이면...
        if [ "$EXTENSION" == "java" ] || [ "$EXTENSION" == "xml" ] || [ "$EXTENSION" == "jsp" ] || [ "$EXTENSION" == "properties" ]; then
            # 파일 인코딩 검사
            ENCODING=$(file -bi "$file" | sed -e 's/.*[ ]charset=//')
            # 인코딩이 iso-8859-1로 나타나면, EUC-KR로 간주하고, 그것을 UTF-8로 변환
            if [ "$ENCODING" == "iso-8859-1" ]; then
                # UTF-8로 변환하고 덮어쓰기
                iconv -f "EUC-KR" -t utf-8 "$file" > "$file.utf8"
            fi
        fi
    fi
done
