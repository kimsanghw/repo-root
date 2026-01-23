erDiagram
  CROP_CLASS_L1 ||--o{ CROP_CLASS_L2 : has
  CROP_CLASS_L2 ||--o{ CROP_CLASS_L3 : has
  CROP_CLASS_L3 ||--o{ CROP : groups

  CROP ||--o{ DISEASE : has
  DISEASE ||--|| DISEASE_DETAIL : detail
  DISEASE ||--o{ DISEASE_IMAGE : images

  PATHOGEN_GROUP ||--o{ DISEASE_PATHOGEN_GROUP : maps
  DISEASE ||--o{ DISEASE_PATHOGEN_GROUP : maps

  %% --- 작물 분류(상세검색 kncr1~3) ---
  CROP_CLASS_L1 {
    varchar code1 PK      "예: FC/VC/FT..."
    varchar name1         "대분류명"
    tinyint is_active
  }

  CROP_CLASS_L2 {
    varchar code2 PK
    varchar code1 FK
    varchar name2
    tinyint is_active
  }

  CROP_CLASS_L3 {
    varchar code3 PK
    varchar code2 FK
    varchar code1 FK
    varchar name3
    tinyint is_active
  }

  %% --- 작물 마스터 ---
  CROP {
    bigint crop_id PK
    varchar crop_name     "예: 논벼/토마토"
    varchar code1 FK      "대분류"
    varchar code2 FK      "중분류"
    varchar code3 FK      "소분류"
    %% UNIQUE(crop_name, code1, code2, code3)
  }

  %% --- 병(질병) 마스터: SVC01 결과 중심 ---
  DISEASE {
    varchar sick_key PK      "예: D00000765 (문서상 Integer라도 문자열이 안전)"
    bigint crop_id FK
    varchar sick_name_kor
    varchar sick_name_chn
    varchar sick_name_eng
    datetime fetched_at      "API에서 가져온 시각(캐시/동기화용)"
    %% INDEX(crop_id)
    %% INDEX(sick_name_kor)
    %% INDEX(sick_name_eng)
  }

  %% --- 병 상세: SVC05(병 상세)에서 오는 텍스트 저장용 ---
  DISEASE_DETAIL {
    varchar sick_key PK FK
    longtext symptoms                "증상"
    longtext development_condition   "발생 생태/조건"
    longtext infection_route         "전염 경로"
    longtext prevention_method       "예방 방법"
    longtext biology_control         "생물적 방제"
    longtext chemical_control        "화학적 방제"
    datetime fetched_at
  }

  %% --- 병 이미지: thumb/ori + 확장(여러장) ---
  DISEASE_IMAGE {
    bigint img_id PK
    varchar sick_key FK
    varchar thumb_url
    varchar ori_url
    int sort_order
    datetime created_at
  }

  %% --- 병원체 그룹(곰팡이/세균/바이러스 등)만 저장 ---
  PATHOGEN_GROUP {
    smallint group_id PK
    varchar group_name     "예: 곰팡이/세균/바이러스/선충/기타"
    %% UNIQUE(group_name)
  }

  %% --- 병-병원체그룹 매핑 (병 하나에 그룹 1개가 보통이지만 N:M으로 안전하게) ---
  DISEASE_PATHOGEN_GROUP {
    bigint map_id PK
    varchar sick_key FK
    smallint group_id FK
    datetime created_at
    %% UNIQUE(sick_key, group_id)
  }
