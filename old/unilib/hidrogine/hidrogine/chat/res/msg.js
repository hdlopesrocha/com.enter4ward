function getmsg(i){
	var div = document.getElementById('msgs');

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if (xhr.readyState == 4  && xhr.status == 200) {
			div.innerHTML += xhr.responseText;
			div.scrollTop = div.scrollHeight;
			getmsg(i+1);
		}
    }
    xhr.open( "GET","/msn?n="+i, true );
    xhr.send( null );
}

function handleKeyPress(e,input){
	var key=e.keyCode || e.which;
	if (key==13){
	
    var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
        if (xhr.readyState == 4  && xhr.status == 200) {

		}
    }


       xhr.open("POST","/chat",true);
       xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
       xhr.send("message="+input.value);
		input.value="";

	}
}
