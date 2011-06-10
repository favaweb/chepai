function $show(pic) {
	var x = event.clientX;
	var y = event.clientY;
	document.getElementById("product").style.display = "block";
	document.getElementById("product").style.pixelLeft = document.body.scrollLeft
			+ x;
	document.getElementById("product").style.pixelTop = document.body.scrollTop
			+ y;

	document.getElementById("product").innerHTML = "<IMG width='300px' height='320px' SRC='images/product/"
			+ pic + "'>";
}

function $hidden() {
	document.getElementById("product").style.display = "none";
}
function show(o) {
	// 重载验证码,为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳
	var timenow = new Date().getTime();
	o.src = "servlet/image?d=" + timenow;
	/*
	 * //超时执行; setTimeout(function(){ o.src="random.jsp?d="+timenow; } ,20);
	 */
}