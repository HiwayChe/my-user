<@c.page title="在线日志">
	<div id="log-container" style="height: 100%; overflow-y: scroll; background: #333; color: #aaa; padding: 10px;">
        <div>
        </div>
    </div>

		<script type="text/javascript">
			window.setTimeout(function(){
				$(document).ready(function() {
			        window.websocket = new WebSocket('ws://localhost:8081/my-user/ws.do?file=2016-11-22.log');
			        websocket.onopen =function(){
			         	websocket.send("ping");
			        };
			        websocket.onclose=function(){
			        	$("#log-container div").append("===========再见======");
			        };
			        websocket.onmessage = function(event) {
			            $("#log-container div").append(event.data);
			            $("#log-container").scrollTop($("#log-container div").height() - $("#log-container").height());
			        };
			    });
			},3000);
		</script>
</@c.page>