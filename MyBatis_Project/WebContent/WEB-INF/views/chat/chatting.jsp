<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="../common/menubar.jsp" />
	
	<div class="outer" >
		<br>
		<h1 align="center" >채팅</h1>
		<br>
		
		<div id="msgContainer" >
			<input type="text" id="receiver" size="10" placeholder="받는사람"> 
			<input type="hidden" id="sender" size="10" value="${empty loginUser ? 'admin' : loginUser.userId}" >
			<input type="text" id="msg"  placeholder="전송할 메세지"> 
			<button onclick="sendMsg()">전송</button>			 
		</div>
		
		
		<script>
			// 웹소켓 서버에 연결하기
		
			// WebSocket객체 생성하기 내IP:192.168.30.186
<%-- 			const socket = new WebSocket("ws://192.168.30.18:8083<%= request.getContextPath() %>/chatting.do"); --%>
<%-- 			const socket = new WebSocket("ws://192.168.30.18:8083<%= request.getContextPath() %>/chatting2.do"); --%>
<%-- 			const socket = new WebSocket("ws://192.168.30.18:8083<%= request.getContextPath() %>/chatting3.do"); --%>
			const socketAddress = "ws://localhost:8083<%= request.getContextPath() %>/chatting3.do"
			let socket = new WebSocket(socketAddress);
			
			// https://localhost:8083/ ...
			// http -> ws:localhost:8083/...
			// htpps -> wss:localhost:8083/...
			
			// socket 설정하기
			// 1. 접속후 실행되는 이벤트핸들러 등록하기
			socket.onopen = function (e) {
				console.log(e);
				console.log("웹소켓 접속성공!");
				
			}
			// 2. 웹소켓서버에서 sendText/sendObjcet메서드를 호출하면 실행되는 함수
			socket.onmessage = function (e) {
				console.log("메세지 수신");
				
				// 수신된 데이터는 이벤트 객체의 data속성에 담겨 있음
				console.log(e.data);
				
				// jsonObject로 전달된 메세지 파싱
				const msg = JSON.parse(e.data);
				
				if(msg["sender"] == $("#sender").val()) {
					$("#msgContainer").append($("<p>").text("["+ msg["sender"] +"]" + msg["msg"]).css("text-align","left") )
					
				}else{
					$("#msgContainer").append($("<p>").text("["+ msg["sender"] +"]" + msg["msg"]).css("text-align","right") )
					
				}
			}
			// 4. 웹소켓세션이 종료될때
			socket.onclose = function (e) {
				// 웹소켓 재연결
				setTimeout(function () {
					socket = new WebSoket(socketAddress);
				}, 1000);
			}
			
			
			// 3. 웹소켓서버에 메세지를 전송하는 함수
			const sendMsg = () => {
				
				// 전송할 메세지 셋팅
				// 메세지를 전송하는 함수 : socket.send('데이터');
				// 발송자, 수신자, 메세지내용
				
				
// 				const msg = {
// 								"sender" : $("#sender").val(),
// 								recevier : $("#recevier").val(),
// 								msg	 :$("#msg").val()
// 							};
				const msg = new Message($("#sender").val(), $("#receiver").val(), $("#msg").val() );
				socket.send( JSON.stringify(msg) );
				// 반드시 문자열형태로 전달하기 위해 JSON객체로 변환 후 전송함
				
				console.log("sender : " + sender);
				console.log("receiver : " + receiver);
				}
			
			
// 			function Message(sender, recevier, msg) {
// 				this.sender = sender;
// 				this.recevier = recevier;
// 				this.msg = msg;
// 			}
			
			class Message{
				constructor(sender, receiver, msg){
					this.sender = sender;
	 				this.receiver = receiver;
	 				this.msg = msg;
				}
			}
			
			
		</script>
	
	</div>
	
	
	
	
	
	
	
	
	
	
	
	
</body>
</html>