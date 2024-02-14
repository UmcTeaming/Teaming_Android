# Teaming_Android

### 프로젝트 제목 : Teaming
우리는 팀프로젝트를 진행하면서 다양한 어려움에 직면하게 됩니다.
파일들이 많아서 어떻게 관리해야 하는지, 어느 파일에 어떤 피드백을 받았는지 혹은 노션과 같은 타 서비스들을 사용하기 어렵다는 등의 여러 어려움들이 있습니다.
이런 문제들을 ‘효율적인 파일 업로드’, ‘일정 달력’,’자신만의 포트폴리오’와 같은 기능들을 구현해서 해결하고자 하는 목적으로 Teaming 앱을 제작했습니다. 

### 프로젝트 개발 기간: 2023.06.27 ~ 2023. 09.08
GitHub 연결과 동시에 개발 시작 + UMC DEMODAY 최우수상 수상으로 NE(O)DINARY DEMODAY 부스 운영을 위해 9월 8일까지 개발되었음 (약 2-3달)
*배포를 목적으로 개발되었기 때문에 현재 리디자인이 진행되고 있고 추후에 배포를 위해 개발 수정이 들어갈 예정

### 담당한 부분_Kotlin 개발
1) 개발 시작을 위한 GitHub 연결
2) 하단 네비게이션바 구현 및 해당 화면 이동 구현
3) 메인 페이지 구현 및 리사이클러뷰 구현 
4) 파일 페이지 구현 및  리사이클러뷰 구현 
5) 프로젝트 생성을 위한 Create 페이지 생성 
6) 메인페이지 내부의 뷰페이저 구현으로 디자인 완성
7) 리스트 페이지 구현 및 리사이클러뷰 구현
8) 메인페이지 내부 API 연결 및 데이터 파싱 구현 
9) 파일페이지 내부 API 연결 _ 이미지 연결 등
10) 세부 다이알로그 생성 및 내부 카메라, 갤러리 연결 + API 구성 
11) 리스트페이지 API 연결 _ 이미지 연결 등
12) 프로젝트 수정을 위한 Modify 페이지 생성 및 API 연결
13) 비밀번호 찾기 페이지 생성 및 API 연결
14) 비밀번호 변경 페이지 생성 및 API 연결
15) 회원가입 부분 API 연결 
16) 완성된 앱 바탕으로 APK 추출


__UMC DEMODAY 이후 피드백을 바탕으로 디자인 수정 및 기능 개발의 필요성을 느껴 추가적인 기능 구현함__
1) 담당한 페이지 부분 디자인 수정 + 폰트 수정
2) 프로젝트 생성 페이지 내부 API 변경 및 기능 변경
3) 파일 페이지에서 부분 검색 기능 구현 + 디자인 수정 
4) 프로젝트 삭제 API 연결 및 기능 추가 
5) 프로젝트 수정 페이지 기능 변경 및 오류 해결


## 담당한 부분에서의 상세 내용 설명
**1) 하단 네비게이션 바 구현 및 화면 이동**
- 메뉴 생성 및 Fragment 생성으로 메뉴 선택 시 설정한 Fragment로 이동하도록 구현

**2) 메인 페이지 _ MainFragment**
- 메인 페이지 내부의 리사이클러 뷰 구현 : HorizontalAdapter, VerticalAdapter, GridAdapter 등 메인화면에서 필요한 3가지 리사이클러뷰 연결을 위한 어댑터와 Itemlist를 생성함
- 해당 리사이클러뷰에 맞게 API 연결 : 
프로젝트 생성을 위한 버튼 추가 및 CreateFragment 연결 + API 연결 및 ScrollView를 이용해서 화면 스크롤이 가능하도록 함

![image](https://github.com/UmcTeaming/Teaming_Android/assets/68581876/f6684f46-8d97-462b-bd63-d7a91c7daa21)
![image](https://github.com/UmcTeaming/Teaming_Android/assets/68581876/ac2f3be7-1491-4973-94ae-93ed315901f4)

**3) 파일 프래그먼트_FileFragment**
- 두가지 버튼을 이용해서 버튼 클릭 시 다른 구성으로 프로젝트가 보이도록 구현
- 해당 뷰도 리사이클러뷰 여러 개를 이용해서 업데이트되는 내용들을 리스트형태로 볼 수 있도록 구현
- 리사이클러뷰와 어댑터에 API를 모두 연결해서 서버로부터 데이터를 연결하도록 함

![image](https://github.com/UmcTeaming/Teaming_Android/assets/68581876/2536a456-658b-4d18-bbdf-c216b65778b3)
![image](https://github.com/UmcTeaming/Teaming_Android/assets/68581876/b5ad4f70-b734-4b9f-b6d3-509bdf8ff2e9)
![image](https://github.com/UmcTeaming/Teaming_Android/assets/68581876/64d5084e-8151-4790-a577-534387755fb8)

**4) 리스트프래그먼트_ListFragment**
- 두가지 버튼을 이용해서 버튼 클릭 시 다른 구성으로 프로젝트가 보이도록 구현
- 해당 뷰도 리사이클러뷰 여러 개를 이용해서 업데이트되는 내용들을 리스트형태로 볼 수 있도록 구현
- 리사이클러뷰와 어댑터에 API를 모두 연결해서 서버로부터 데이터를 연결하도록 함
![image](https://github.com/UmcTeaming/Teaming_Android/assets/68581876/f8f1bde0-9c37-4326-8c5a-02fa1b291558)
![image](https://github.com/UmcTeaming/Teaming_Android/assets/68581876/9e3b6fe8-a176-4b03-900e-41058e08324d)
![image](https://github.com/UmcTeaming/Teaming_Android/assets/68581876/6c2aabf4-105d-4d30-9ff7-160605ca8480)

**5) 프로젝트 생성 페이지_CreateFragment**
- 이미지 선택 화면을 통해서 3가지 이미지 선택 방법 구현 : 카메라, 갤러리, 기본 이미지
- 색상 선택 부분 구현
- 날짜 선택 => DatePicker를 이용
- CustomDialog 생성을 통해 사이트에 맞는 알림창을 생성
- API 연결 : 이미지, 날짜, 프로젝트명, 색상 등을 서버로 넘기는 API 연결
![image](https://github.com/UmcTeaming/Teaming_Android/assets/68581876/5a362533-52e0-4fbd-96b4-bd6f995b4fb7)
![image](https://github.com/UmcTeaming/Teaming_Android/assets/68581876/e9a21de7-e1d2-4aca-a173-e3469933fef7)
![image](https://github.com/UmcTeaming/Teaming_Android/assets/68581876/ec7ef8f7-ab97-4e80-b672-3bc51532c4b9)
![image](https://github.com/UmcTeaming/Teaming_Android/assets/68581876/5c242176-4cb7-4a55-aa82-b57429351adf)

**6) 프로젝트 수정 페이지_ModifyFragment**
- 프로젝트 생성 페이지와 비슷하지만, 인원 추가에서 리사이클러뷰와 Glide를 이용해 사진이 보이도록 함
- API PATCH를 연결해 서버에서의 데이터를 수정할 수 있도록 함
![image](https://github.com/UmcTeaming/Teaming_Android/assets/68581876/82a678d5-e597-4623-bac6-8ad5e3b76721)
![image](https://github.com/UmcTeaming/Teaming_Android/assets/68581876/62ed3fd5-4fdd-4c4c-a469-9b8675636c49)
![image](https://github.com/UmcTeaming/Teaming_Android/assets/68581876/db1bfc96-bdba-4b0e-9008-65b4220906ce)

**7) 비밀번호 변경 페이지**
- 화면 구성은 다른 팀원이 하고, 해당 페이지에 대한 API 연결을 함
- API PATCH를 이용해 기존의 서버에서의 데이터를 변경하는 과정
![image](https://github.com/UmcTeaming/Teaming_Android/assets/68581876/5f978d4f-9076-44f7-ad24-d356472b5ef5)
![image](https://github.com/UmcTeaming/Teaming_Android/assets/68581876/24c8ca6b-361e-4e87-8ec5-7853d30f323d)
![image](https://github.com/UmcTeaming/Teaming_Android/assets/68581876/7b244a64-230d-41fd-b080-cbabe8b4fd31)
![image](https://github.com/UmcTeaming/Teaming_Android/assets/68581876/b612e1c4-e5ef-4010-9cf1-29ccffb00ce1)
![image](https://github.com/UmcTeaming/Teaming_Android/assets/68581876/06cc1f6d-1054-45d9-9764-69d1edb6c0f9)
![image](https://github.com/UmcTeaming/Teaming_Android/assets/68581876/52c3c3e8-56bd-4809-8345-cad804baf128)

**8) 비밀번호 찾기 페이지**
- 기존에 서버에 있는 데이터를 확인해서 비밀번호를 받을 수 있도록 API 연결을 진행
- 화면 구성은 다른 팀원이 하고, 해당 페이지에 대한 API 연결을 함
![image](https://github.com/UmcTeaming/Teaming_Android/assets/68581876/34ef0d63-bdb1-4d1f-b135-f8603dada99f)
![image](https://github.com/UmcTeaming/Teaming_Android/assets/68581876/182e0422-1e1f-4e36-93e0-487f0d764929)
![image](https://github.com/UmcTeaming/Teaming_Android/assets/68581876/042c24c8-0023-4450-8120-19e3e4dd0268)
![image](https://github.com/UmcTeaming/Teaming_Android/assets/68581876/1acb9d56-8edb-4919-812c-84853c60df14)

**이외에도 다양한 부분들에 참여했으나 너무 길어지는 것을 우려해 주요한 기능들을 정리해 두었습니다.더 자세한 내용은 깃허브나 아래의 영상을 통해서 더 자세히 확인하실 수 있습니다.**

**[Teaming_시연영상]**
https://drive.google.com/file/d/1uX3lbgpZ5KwUfnrkoTW8RVTOPsyCE572/view?usp=sharing





