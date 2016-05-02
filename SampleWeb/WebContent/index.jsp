<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>Achievement Board</title>
<head>
</head>
<script type="text/javascript">
	var source;
	function clearevents() {
		document.getElementById('events').innerHTML = "";
	}

	function start() {

		source = new EventSource('mySSE');
		source.onopen = function(event) {
			console.log("eventsource opened!");
		};

		source.onmessage = function(event) {
			var data = event.data;
			//console.log(data);

			var events = data.split("|");
			for (var i = 0; i < events.length; i++) {
				var dataString = events[i];
				var substring = "got achievement";

				if (dataString.indexOf(substring) > -1) {
					document.getElementById('achievements').innerHTML = dataString
							+ "<br />"
							+ document.getElementById('achievements').innerHTML;
				} else if (dataString.trim().length != 0) {
					document.getElementById('events').innerHTML = dataString
							+ "<br />"
							+ document.getElementById('events').innerHTML;
				}
			}
		};

		document.getElementById("start").disabled = true;
		document.getElementById("stop").disabled = false;

	}
	function stop() {
		source.close();
		document.getElementById("start").disabled = false;
		document.getElementById("stop").disabled = true;
	}
</script>
<body style="height: 500px">
	<div align="center">
		<table>
			<tr>
				<td><span>Game Play Simulation : </span></td>
				<td><button id="start" onclick="start()">Start</button></td>
				<td><button id="stop" disabled="disabled" onclick="stop()">Stop</button></td>
			</tr>
		</table>
	</div>
	<div align="center">
		<table style="width: 100%; border: solid; height: 500px">
			<tr>
				<td align="center"><div>
						<font face="verdana" color="blue">Events</font>
					</div></td>
				<td align="center"><div>
						<font face="verdana" color="green">Achievements</font>
					</div></td>
			</tr>
			<tr style="width: 100%">
				<td align="left"><div
						style="height: 500px; overflow-y: scroll; overflow-x: hidden"
						id="events"></div></td>
				<td align="left" height="300px"><div
						style="height 500px: scroll; overflow-x: hidden" id="achievements"></div></td>
			</tr>
			<tr>
				<td align="center" colspan="2"><button id="clear" onclick="clearevents()">clear</button></td>

			</tr>

		</table>


	</div>
</body>
</html>