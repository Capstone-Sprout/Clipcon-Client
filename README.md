# ClipCon
ClipCon은 [Global Clipboard](#global-clipboard)에서 사용할 수 있는 클라이언트 어플리케이션의 이름입니다. 현재 본 프로젝트에서는 윈도우와 안드로이드에서 구동되는 어플리케이션을 개발하였습니다. <u>이 repository는 윈도우 버전의 ClipCon에 대한 내용입니다.</u> 안드로이드 버전의 ClipCon에 대해서는 다음을 참고하여 주시가 바랍니다.
* 안드로이드: [Android ClipCon GitHub](https://github.com/team-sprout/clipcon-AndroidClient)

## 사용 방법
### 1. 접속
Global Clipboard의 사용자들의 단위는 '그룹'입니다. 사용자는 그룹을 생성하고 거기에 참여할 수 있으며, 그룹 내의 사용자끼리 데이터를 교환합니다. 그룹을 생성하면 그룹의 고유한 Key를 발급받을 수 있고 다른 참여자들은 접속 시 Key를 입력하여 같은 그룹에 소속될 수 있습니다.
### 2. 전송(업로드)
전송하기를 원하는 데이터를 복사한 후, 다음과 같은 행동을 하면 클립보드의 데이터가 서버에 업로드 됩니다.
* 화면 우측 하단의 버튼(복사 후 5초간 생성)을 클릭
* 단축키 Ctrl Shift C를 입력
### 3. 수신(다운로드)
새로운 데이터가 업로드 되면 화면 우측 하단에 알림이 표시됩니다. 다음과 같은 행동을 하면 업로드 된 데이터를 자신의 클립보드에 삽입합니다.
* 업로드 알림(5초간 생성)을 클릭
* 단축키 Ctrl Shift V를 입력

## Demo
[이 곳](http://113.198.84.53/globalclipboard/download)이나 [release](https://github.com/Team-Sprout/Clipcon-Client/releases) 탭에서 ClipCon을 다운받아 실행해 볼 수 있습니다.


## [Global Clipboard](#global-clipboard)
Global Clipboard는 다수의 사용자가 클립보드를 이용하여 데이터를 간편하게 주고 받을 수 있는 Server-Client
구조의 데이터 전송 플랫폼입니다. 사용자는 클립보드를 이용한 복사, 붙여넣기 인터페이스와 이에 접근 가능한 단축키 (Ctrl C, Ctrl V)를 이용하여 다른 번거로운 과정 없이 간편하게 데이터를 주고 받을 수 있습니다. 따라서 다른 사용자가 복사한 데이터를 자신이 붙여넣는 것 처럼 사용할 수 있습니다. 클립보드를 이용하기 때문에 클립보드에 복사할 수 있는 모든 종류의 데이터(Text, Capture Image, File)를 전송할 수 있습니다.

Global Clipboard Server에 대한 내용은 해당 repository를 참고하여 주시기 바랍니다.
* [Global Clipboard Server GitHub](https://github.com/team-sprout/clipcon-Server)

### 유용성
Global Clipboard는 다수의 다바이스 사이에서 데이터를 주고 받는 상황이라면 어디서든 활용가능하지만, 신뢰있는 사용자간에 전송할 데이터가 간헐적으로 발생할 때 가장 효과적으로 사용할수 있습니다.
* 1인 2PC 이상 사용자의 PC간 데이터 교환
  * 예) 작업용 데스크탑과 개인용 노트북 간 텍스트 또는 파일 복사/붙여넣기 등
* 다수의 사용자와 협업 시, 데이터 전송 및 교환
  * 예1) 팀원과 함께 발표 자료를 만들 때 적절한 캡처 이미지 복사/붙여넣기
  * 예2) 교수자가 학생들에게 즉석에서 필요한 수업자료를 클립보드를 이용하여 배포 등

<!-- ## Windows ClipCon의 구조 -->
<!-- ![그림](https://www.evernote.com/shard/s480/sh/a71ae9a5-90b8-4f8c-ac1d-68edb07ec40a/2fc89c0c5be3e9fe/res/8a9857f2-60c3-4859-985a-3335abbe5d0b/GlobalClipboardArchitecture.png?resizeSmall&width=832) -->

<!-- <img src="https://www.evernote.com/shard/s480/sh/a71ae9a5-90b8-4f8c-ac1d-68edb07ec40a/2fc89c0c5be3e9fe/res/8a9857f2-60c3-4859-985a-3335abbe5d0b/GlobalClipboardArchitecture.png?resizeSmall&width=832" alt="alt text" width="80%" align="center"> -->
