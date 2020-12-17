# MarbleProject

## 서버 구동 방법
**MableLauncher.java**  
MarbleLauncher.java를 구동하면 MarbleServer가 실행됩니다.<br/><br/>

## 클라이언트 구동 방법
**MarbleLogin.java**  
		MarbleServer가 실행된 상태면, MarbleLogin.java를 통해 아이디를 입력한 후 로그인할 수 있습니다.<br/><br/>

<<<<<<< HEAD
**강조**
# 큰글씨
## 중간글씨
- li  
> 보기좋게!  

=======
## 활용 라이브러리
** gson ** : 서버와 클라이언트 간 소켓 통신 시 toJson, fromJson을 통해 RequestDto 클래스를 이용하였습니다.  
** lombok ** : Player 객체, Tile 객체 내부의 변수를 get/set 하기 위해 이용하였습니다.
>>>>>>> 1634cb42243da037a230bef7a5e4f30b08629fff

<br/><br/><br/>

<<<<<<< HEAD
=======
# 게임 구동 영상
 - 링크 넣을곳<br/><br/>
>>>>>>> 1634cb42243da037a230bef7a5e4f30b08629fff

<br/><br/>

## 로그인
![image](https://user-images.githubusercontent.com/63082842/102418718-a1690b80-4041-11eb-9727-bb08a63705b5.png)<br/><br/>
 - 게임을 실행하면 아이디를 설정해야 합니다.<br/><br/>

![image](https://user-images.githubusercontent.com/63082842/102418004-3e2aa980-4040-11eb-998a-f9c654a79cc1.png)<br/><br/>
 - 빈 채로 로그인시 에러 메시지가 출력됩니다.<br/><br/>

![image](https://user-images.githubusercontent.com/63082842/102418036-4e428900-4040-11eb-9593-c265644d6188.png)<br/><br/>
 - 6자 이상 입력시 에러 메시지가 출력됩니다.<br/><br/>

<br/><br/>

## 초기화면
![image](https://user-images.githubusercontent.com/30206820/102386975-2983ec80-4013-11eb-896f-ccd4d646e4da.png)<br/><br/>
 - 좌측에는 게임을 할 수 있는 보드판이 있으며 우측에는 캐릭터 정보창과 채팅창이 있습니다.<br/><br/>
 - 기본적으로는 2명 이상이어야 게임을 시작할 수 있으며 최대 4인까지 가능합니다.

### 기존에 존재하는 닉네임으로 접속시 에러창이 발생합니다.
![image](https://user-images.githubusercontent.com/30206820/102387392-a9aa5200-4013-11eb-81fe-2286af08b572.png)

### 게임이 이미 시작중이거나 4명이 초과되면 에러창이 발생합니다.
![image](https://user-images.githubusercontent.com/30206820/102387665-0148bd80-4014-11eb-8df2-6ad227ca7b42.png)

### 가장 빨리 접속한 플레이어에게 게임 시작 버튼이 보이게 됩니다.
![image](https://user-images.githubusercontent.com/30206820/102388188-c1360a80-4014-11eb-86cf-8f98c3204318.png)

<br/><br/>

## 게임 
![image](https://user-images.githubusercontent.com/30206820/102388343-f5a9c680-4014-11eb-89b2-66f555f99390.png)<br/>
 - 게임을 시작하게 되면 플레이어의 수 만큼 플레이어 케릭터가 등장하게 됩니다.<br/>
 - 시작한 플레이어 부터 차례대로 턴을 진행하게 됩니다.<br/>
 - 보여지는 화면은 모든 플레이어가 동일하게 보여집니다.<br/>

### 주사위를 굴리면 플레이어가 움직입니다.
![image](https://user-images.githubusercontent.com/30206820/102388657-68b33d00-4015-11eb-839d-d67c7c8f5587.png)

### 스타트 지점을 통과하게 되면 월급을 받을 수 있습니다.
![image](https://user-images.githubusercontent.com/30206820/102389611-af556700-4016-11eb-9c5e-773bcce959cc.png)

<br/><br/>

## 구매

### 타일이 섬 또는 도시이고 소유주가 없다면 구매를 할 수 있습니다.
![image](https://user-images.githubusercontent.com/30206820/102388924-c6478980-4015-11eb-8eaa-922fbb9026bc.png)

![image](https://user-images.githubusercontent.com/30206820/102389176-158dba00-4016-11eb-8070-8a22d1581c3f.png)

### 도시의 경우 땅, 집, 빌딩, 호텔을 살 수 있으며 땅을 사지 않으면 구매할 수 없습니다.
![image](https://user-images.githubusercontent.com/30206820/102389336-508fed80-4016-11eb-8253-48c3b15132a0.png)<br/>
 - 체크박스를 누르면 구매 예상가격이 나오게 됩니다.<br/>

### 구매 후
![image](https://user-images.githubusercontent.com/30206820/102389419-71584300-4016-11eb-93de-664ec225147c.png)<br/>
 - 구매를 하게되면 그 타일의 이름칸 색깔이 나의 색으로 바뀌게 됩니다.<br/>
 - 보유하고 있는 자산에서 금액이 차감됩니다.<br/>
 - 턴 종료버튼이 보여지게 됩니다.

<br/><br/>

## 벌금
![image](https://user-images.githubusercontent.com/30206820/102390192-6fdb4a80-4017-11eb-9798-5081f0f5ab18.png)<br/>
 - 섬이나 도시 타일이지만 소유주가 다른 경우에는 해당 소유주에게 벌금을 내야합니다.<br/>

![image](https://user-images.githubusercontent.com/30206820/102390791-37883c00-4018-11eb-9e6f-9794f02320ef.png)<br/>
 - 만약 보유한 금액이 벌금보다 적다면 탈락하게 됩니다.<br/>
 
![image](https://user-images.githubusercontent.com/30206820/102392094-f8f38100-4019-11eb-9622-df6573eafa24.png)<br/>
 - 문구가 출력되며 채팅창에도 알림이 공지됩니다.
 
<br/><br/>

## 특수 타일
![image](https://user-images.githubusercontent.com/30206820/102389787-f3486c00-4016-11eb-99c5-ea12e4c07e5e.png)<br/>
 - 황금 열쇠가 그려진 칸에 도달하게 되면 특수한 이벤트가 발생합니다.

### 세계여행에 도착하게 되면 일단은 아무일도 일어나지 않습니다.
![image](https://user-images.githubusercontent.com/30206820/102390055-4a4e4100-4017-11eb-9779-aefcf0e77aa3.png)<br/>
 - 새로운 차례가 시작되면 세계여행을 제외한 원하는 타일을 선택할 수 있습니다.<br/>
![image](https://user-images.githubusercontent.com/30206820/102390946-669ead80-4018-11eb-9350-0ff192622c50.png)<br/>
 - 버튼을 클릭하면 이동 후 액션을 수행합니다.<br/>
![image](https://user-images.githubusercontent.com/30206820/102391096-9948a600-4018-11eb-8582-75743437d024.png)<br/>

### 올림픽 타일에 도착하게 되면 자신의 타일 중 한 곳을 선택하여 통행료를 2배로 받을 수 있습니다.
![image](https://user-images.githubusercontent.com/30206820/102391328-e88ed680-4018-11eb-8d25-1124118392f6.png)

<br/><br/>

## 기타 기능

### 승리 문구
![image](https://user-images.githubusercontent.com/30206820/102392251-2c361000-401a-11eb-888f-970583bb638c.png)
- 타일을 클릭하면 정보창이 보여집니다.<br/>
![image](https://user-images.githubusercontent.com/30206820/102392466-70c1ab80-401a-11eb-9364-ffee21a64131.png)
![image](https://user-images.githubusercontent.com/30206820/102392555-8df67a00-401a-11eb-8fb1-7d80ae7dd70c.png)

<br/><br/>

## 사용한 프로토콜

### IDSET
** 플레이어 로그인 시 로그인창에 입력한 ID값을 서버로 전송하는 기능 **

### IDCHECK
** IDSET 시 이미 서버상에 존재하는 ID면 접속이 불가능하게 하는 기능 **  

### GAMEHOST
** 방장이 된 플레이어의 클라이언트의 시작버튼을 활성화하는 기능 **  
<ol>
	<li> 플레이어가 접속할 때마다 서버의 playerList(접속중인 플레이어를 담은 Vector)를 확인합니다.
	<li> 2명 이상이 되면 playerList의 0번 인덱스 유저 클라이언트의 '시작버튼'을 활성화합니다.
</ol>

### GAMESTART
** 방장이 '시작버튼'을 누르면 서버의 시작여부 함수를 변경하고 시작을 알리는 기능 **
<ol>
	<li> 서버의 시작여부 변수(isPlaying)를 true로 변경합니다. // default : false  
	<li> PLAYERSET 프로토콜을 사용하여 클라이언트의 Player 객체의 ID값을 변경합니다.
	<li> CHAT 프로토콜을 사용하여 모든 플레이어에게 게임 시작을 알립니다.
	<li> INITTURN 프로토콜을 사용하여 방장 플레이어에게 턴을 넘겨줍니다.
</ol>
	

### PLAYERSET
** 서버로부터 각 플레이어의 ID값을 받아 클라이언트에 있는 Player 객체의 ID값을 변경하는 기능 **  
<ol>
	<li> 서버의 countPlayer 변수를 접속중인 플레이어 수만큼 지정합니다. (승리/패배 확인을 위해)
	<li> 플레이어의 수만큼 차례대로 클라이언트의 player1, 2, 3, 4의 ID값을 설정해줍니다.
	<li> 클라이언트는 ID값이 존재하는 플레이어들의 이미지를 활성화시킵니다.
	<li> 또한, 오른쪽 플레이어 정보창도 활성화시킵니다.
</ol>

### DICEROLL
** 클라이언트의 '주사위 굴리기' 버튼을 누르면 1~6 랜덤값 두 개를 서버로 보내는 기능 **  
<ol>
	<li> 클라이언트에 존재하는 '주사위 굴리기' 버튼을 누르면 playerRoll 함수가 실행됩니다.
	<li> playerRoll 함수가 실행되면, DICEROLL 프로토콜을 사용하여 서버로 주사위 값을 보냅니다.
	<li> 서버는 받은 주사위값을 접속중인 모든 클라이언트에게 보냅니다.
	<li> 클라이언트는 서버로부터 받은 주사위값을 클라이언트 화면에 출력합니다.
</ol>

### MOVE
** 주사위를 굴린 플레이어의 캐릭터의 발판위치를 변경하는 기능 **  
<ol>
	<li> 주사위를 굴린 플레이어의 ID를 받아서 player1, 2, 3, 4 중 해당 플레이어 번호를 찾습니다.
	<li> 클라이언트에서는 플레이어 객체의 moveAnimation을 이용하여 확인된 해당 플레이어 번호의 캐릭터를 움직입니다.
</ol>

# DIALOGREQUEST  
**다이얼로그 정보 요청**  
<ol>
	<li> move함수가 실행이 되면 플레이어의 위치값과 아이디를 클라이언트에서 서버로 보냅니다. </li>
	<li> 서버에서 플레이어의 위치값과 같은 타일을 타일리스트에서 찾습니다. </li>
	<li> 클라이언트에서 받은 아이디와 같은 클라이언트에게 찾은 타일을 보냅니다. </li>
	<li> 받은 타일을 클라이언트에 static변수로 저장합니다. </li>
</ol>


# DIALOGUPDATE  
**구매하기/올림픽 시 서버와 클라이언트의 TileList Update**  
<ol>
	<li> 플레이어의 행위에 따라 타일의 상태값에 변동이 일어납니다. </li>
	<li> 변동이 일어나게 되면 상태값을 저장 후 클라이언트에서 서버로 타일을 보냅니다. </li>
	<li> 서버에서 타일을 받으면 타일리스트에서 동일한 타일을 찾습니다. </li>
	<li> 기존 타일을 받은 타일의 정보로 수정합니다. </li>
</ol>  


# PLAYERPURCHADED  
**구매 정보 전달**  
<ol>
	<li> 시티 혹은 섬의 타일 넘버와 같은 곳에 걸렸을 시 DIALOGREQUEST에서 받아온 타일의 오너를 확인합니다. </li>
	<li> 오너의 아이디가 존재하지 않으며, 나와 아이디가 같을 경우 실행됩니다. </li>
	<li> 시티 혹은 섬 타일을 구매 시 오너 아이디, 구매 내역, 통행료 정보를 클라이언트에 있는 타일 정보에 저장합니다. </li>
	<li> 변경된 정보를 DIALOGUPDATE를 통해 서버로 보내어 타일리스트에서 동일한 타일에 저장합니다. </li>
	<li> 구매한 플레이어의 아이디를 서버로 전송 후 서버에서 모든 플레이어에게 아이디와 구매금액을 전송 합니다. </li>
	<li> 전송받은 아이디와 동일한 아이디를 가진 플레이어를 찾습니다. </li>
	<li> 찾은 플레이어의 현금을 구매 가격만큼 차감합니다. </li>
</ol>  

# PLAYERFINE  
**통행료 정보 전달**  
<ol>
	<li> 시티 혹은 섬의 타일 넘버와 같은 곳에 걸렸을 시 DIALOGREQUEST에서 받아온 타일의 오너를 확인합니다. </li>
	<li> 오너의 아이디가 존재하며, 나와 아이디가 다를경우, 즉 다른 플레이어 소유의 타일일 경우 실행됩니다. </li>
	<li> 걸린 플레이어의 아이디와 오너 아이디와 통행료를 서버로 전송합니다. </li>
	<li> 서버에서 모든 플레이어에게 아이디와 오너 아이디와 통행료를 전송합니다. </li>
	<li> 전송받은 아이디와 동일한 아이디를 가진 플레이어를 찾습니다. </li>
	<li> 찾은 플레이어의 현금을 통행료 만큼 차감합니다. </li>
	<li> 같은 방식으로 오너 아이디를 가진 플레이어를 찾은 후 찾은 플레이어의 현금에 통행료 만큼 증가시킵니다. </li>
</ol>  

#PLAYERDIE  
**플레이어 게임 아웃 정보 전달**  
<ol>
	<li> 플레이어의 현금이 0원이 되었을 때 실행합니다. </li>
	<li> 현금이 0원이된 플레이어의 아이디를 서버로 전송합니다. </li>
	<li> 타일리스트의 타일에 저장되어있는 오너 아이디가 NULL 값이 아니고 받은 아이디와 타일리스트의 타일에 저장되어있는 오너 아이디와 같은 아이디를 찾습니다. </li>
	<li> 찾은 타일의 오너 정보를 국가로 변경 합니다. </li>
</ol>  

#PLAYERLISTOUT  
**플레이어 리스트 아웃**  
<ol>
	<li> 아웃된 플레이어의 아이디를 서버로 전송합니다. </li>
	<li> 플레이 리스트에 저장되어있는 플레이어의 ISPLAYING 값을 FALSE로 변경합니다. </li>
	<li> 모든 클라이언트에게 False로 변경된 아이디를 전송합니다. </li>
	<li> 클라이언트에서 같은 아이디를 가진 플레이어는 ISPLAYING 값을 FALSE로 변경합니다. </li>
	<li> 모든 클라이언트는 받은 아이디값을 가진 플레이어의 이미지를 FALSE로 변경하여 게임화면에서 지웁니다. </li>
</ol>  