
<!--
//定义当前是否大写的状态
var CapsLockValue=0;
//欢迎来到站长特效网，我们的网址是www.zzjs.net，很好记，zz站长，js就是js特效，本站收集大量高质量js代码，还有许多广告代码下载。
var check;
function setVariables() {
tablewidth=630;  // logo width, in pixels
tableheight=20;  // logo height, in pixels
if (navigator.appName == "Netscape") {
horz=".left";
vert=".top";
docStyle="document.";
styleDoc="";
innerW="window.innerWidth";
innerH="window.innerHeight";
offsetX="window.pageXOffset";
offsetY="window.pageYOffset";
}
else {
horz=".pixelLeft";
vert=".pixelTop";
docStyle="";
styleDoc=".style";
innerW="document.body.clientWidth";

innerH="document.body.clientHeight";

offsetX="document.body.scrollLeft";
offsetY="document.body.scrollTop";
   }
}//欢迎来到站长特效网，我们的网址是www.zzjs.net，很好记，zz站长，js就是js特效，本站收集大量高质量js代码，还有许多广告代码下载。
function checkLocation() {
if (check) {
objectXY="softkeyboard";
var availableX=eval(innerW);
var availableY=eval(innerH);
var currentX=eval(offsetX);
var currentY=eval(offsetY);
x=availableX-tablewidth+currentX;
//y=availableY-tableheight+currentY;
y=currentY;
evalMove();
}
setTimeout("checkLocation()",0);
}
function evalMove() {
//eval(docStyle + objectXY + styleDoc + horz + "=" + x);
eval(docStyle + objectXY + styleDoc + vert + "=" + y);
}//欢迎来到站长特效网，我们的网址是www.zzjs.net，很好记，zz站长，js就是js特效，本站收集大量高质量js代码，还有许多广告代码下载。
 self.onError=null;
 currentX = currentY = 0;
 whichIt = null;
 lastScrollX = 0; lastScrollY = 0;
 NS = (document.layers) ? 1 : 0;
 IE = (document.all) ? 1: 0;
 function heartBeat() {
  if(IE) { diffY = document.body.scrollTop; diffX = document.body.scrollLeft; }
     if(NS) { diffY = self.pageYOffset; diffX = self.pageXOffset; }
  if(diffY != lastScrollY) {
                 percent = .1 * (diffY - lastScrollY);
                 if(percent > 0) percent = Math.ceil(percent);
                 else percent = Math.floor(percent);
     if(IE) document.all.softkeyboard.style.pixelTop += percent;
     if(NS) document.softkeyboard.top += percent;
                 lastScrollY = lastScrollY + percent;}
  if(diffX != lastScrollX) {
   percent = .1 * (diffX - lastScrollX);
   if(percent > 0) percent = Math.ceil(percent);
   else percent = Math.floor(percent);
   if(IE) document.all.softkeyboard.style.pixelLeft += percent;
   if(NS) document.softkeyboard.left += percent;
   lastScrollX = lastScrollX + percent; }  }
 function checkFocus(x,y) {
         stalkerx = document.softkeyboard.pageX;
         stalkery = document.softkeyboard.pageY;
         stalkerwidth = document.softkeyboard.clip.width;
         stalkerheight = document.softkeyboard.clip.height;
         if( (x > stalkerx && x < (stalkerx+stalkerwidth)) && (y > stalkery && y < (stalkery+stalkerheight))) return true;
         else return false;}
 function grabIt(e) {
     check = false;
  if(IE) {
   whichIt = event.srcElement;
  // while (whichIt.id.indexOf("softkeyboard") == -1) {
   // whichIt = whichIt.parentElement;
    //if (whichIt == null) { return true; } }
 //  whichIt.style.pixelLeft = whichIt.offsetLeft;
 //    whichIt.style.pixelTop = whichIt.offsetTop;
   currentX = (event.clientX + document.body.scrollLeft);
      currentY = (event.clientY + document.body.scrollTop);
  } else {
         window.captureEvents(Event.MOUSEMOVE);
         if(checkFocus (e.pageX,e.pageY)) {
                 whichIt = document.softkeyboard;
                 StalkerTouchedX = e.pageX-document.softkeyboard.pageX;
                 StalkerTouchedY = e.pageY-document.softkeyboard.pageY;} }
     return true; }
 function moveIt(e) {
  if (whichIt == null) { return false; }
  if(IE) {
  //    newX = (event.clientX + document.body.scrollLeft);
  //    newY = (event.clientY + document.body.scrollTop);
  //    distanceX = (newX - currentX);    distanceY = (newY - currentY);
  //    currentX = newX;    currentY = newY;
  //    whichIt.style.pixelLeft += distanceX;
  //    whichIt.style.pixelTop += distanceY;
  // if(whichIt.style.pixelTop < document.body.scrollTop) whichIt.style.pixelTop = document.body.scrollTop;
  // if(whichIt.style.pixelLeft < document.body.scrollLeft) whichIt.style.pixelLeft = document.body.scrollLeft;
//   if(whichIt.style.pixelLeft > document.body.offsetWidth - document.body.scrollLeft - whichIt.style.pixelWidth - 20) whichIt.style.pixelLeft = document.body.offsetWidth - whichIt.style.pixelWidth - 20;
  // if(whichIt.style.pixelTop > document.body.offsetHeight + document.body.scrollTop - whichIt.style.pixelHeight - 5) whichIt.style.pixelTop = document.body.offsetHeight + document.body.scrollTop - whichIt.style.pixelHeight - 5;
   event.returnValue = false;
  } else {
   whichIt.moveTo(e.pageX-StalkerTouchedX,e.pageY-StalkerTouchedY);
         if(whichIt.left < 0+self.pageXOffset) whichIt.left = 0+self.pageXOffset;
         if(whichIt.top < 0+self.pageYOffset) whichIt.top = 0+self.pageYOffset;
         if( (whichIt.left + whichIt.clip.width) >= (window.innerWidth+self.pageXOffset-17)) whichIt.left = ((window.innerWidth+self.pageXOffset)-whichIt.clip.width)-17;
         if( (whichIt.top + whichIt.clip.height) >= (window.innerHeight+self.pageYOffset-17)) whichIt.top = ((window.innerHeight+self.pageYOffset)-whichIt.clip.height)-17;
         return false;}
     return false; }
 function dropIt() {whichIt = null;
     if(NS) window.releaseEvents (Event.MOUSEMOVE);
     return true; }
 if(NS) {window.captureEvents(Event.MOUSEUP|Event.MOUSEDOWN);
  window.onmousedown = grabIt;
   window.onmousemove = moveIt;
  window.onmouseup = dropIt; }
 if(IE) {
  document.onmousedown = grabIt;
   document.onmousemove = moveIt;
  document.onmouseup = dropIt; }
 if(NS || IE) action = window.setInterval("heartBeat()",1);
//欢迎来到站长特效网，我们的网址是www.zzjs.net，很好记，zz站长，js就是js特效，本站收集大量高质量js代码，还有许多广告代码下载。
document.write ('    <DIV align=center id=\"softkeyboard\" name=\"softkeyboard\" style=\"position:absolute; top:0;  left:300px;  width:450px; z-index:180;display:none\">');
document.write ('  <table width=\"348\" border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"1\" bgcolor=\"#CAE8EA\">');
document.write ('    <FORM name=Calc action=\"\" method=post autocomplete=\"off\">');
document.write ('      <INPUT type=hidden value=ok name=action2>');
document.write ('      <tr> ');
document.write ('        <td align=\"left\" bgcolor=\"#CAE8EA\" align=\"center\"> <INPUT class=td1b type=text size=20 value=\"\" name=password> ');
document.write ('          <INPUT class=button type=button value=输入完毕 name=\"Submit3\" onclick=\"OverInput(curEditName);\"> <INPUT class=button type=reset value=输错重来 name=\"Submit23\"> ');
document.write ('          <input class=button type=button value=\"关闭\" name=\"Submit222\" onclick=\"closekeyboard(curEditName);\"> </td>');
document.write ('      </tr>');
document.write ('      <tr> ');
document.write ('        <td align=\"center\" bgcolor=\"#FFFFFF\" align=\"center\"> <table align=\"center\" width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">');
document.write ('            <tr align=\"left\" valign=\"middle\"> ');
document.write ('              <td> ');
document.write ('                <input type=button onclick=\"addValue(\'1\');\" value=\" 1 \"></td>');
document.write ('              <td> ');
document.write ('                <input type=button onclick=\"addValue(\'2\');\" value=\" 2 \"></td>');
document.write ('              <td> ');
document.write ('                <input type=button onclick=\"addValue(\'3\');\" value=\" 3 \"></td>');
document.write ('              <td> ');
document.write ('                <input type=button onclick=\"addValue(\'4\');\" value=\" 4 \"></td>');
document.write ('              <td> ');
document.write ('                <input type=button onclick=\"addValue(\'5\');\" value=\" 5 \"></td>');
document.write ('              <td> ');
document.write ('                <input type=button onclick=\"addValue(\'6\');\" value=\" 6 \"></td>');
document.write ('              <td> ');
document.write ('                <input type=button onclick=\"addValue(\'7\');\" value=\" 7 \"></td>');
document.write ('              <td> ');
document.write ('                <input type=button onclick=\"addValue(\'8\');\" value=\" 8 \"></td>');
document.write ('              <td> ');
document.write ('                <input type=button onclick=\"addValue(\'9\');\" value=\" 9 \"></td>');
document.write ('              <td> ');
document.write ('                <input type=button onclick=\"addValue(\'0\');\" value=\" 0 \"></td>');

document.write ('              <td><input name=\"button10\" type=button value=\" BackSpace\" onclick=\"setpassvalue();\"> ');
document.write ('              </td>');
document.write ('              <td> </td>');
document.write ('            </tr>');
document.write ('          </table></td>');
document.write ('      </tr>');
document.write ('    </FORM>');
document.write ('  </table>');
document.write ('</DIV>');
//欢迎来到站长特效网，我们的网址是www.zzjs.net，很好记，zz站长，js就是js特效，本站收集大量高质量js代码，还有许多广告代码下载。
//给输入的密码框添加新值
 function addValue(newValue)
 {
  if (CapsLockValue==0)
  {
   Calc.password.value += newValue;
  }
  else
  {
   Calc.password.value += newValue.toUpperCase();
  }
 }
//实现BackSpace键的功能
 function setpassvalue()
 {
  var longnum=Calc.password.value.length;
  var num
  num=Calc.password.value.substr(0,longnum-1);
  Calc.password.value=num;
 }
//输入完毕
 function OverInput(theForm)
 {
  eval("var theForm="+theForm+";");
  //m_pass.mempass.value=Calc.password.value;
  theForm.value=Calc.password.value;
   //alert(theForm.value);
  //theForm.value=m_pass.mempass.value;
  softkeyboard.style.display="none";
  Calc.password.value="";
 }
//关闭软键盘
 function closekeyboard(theForm)
 {
  //eval("var theForm="+theForm+";");
  //theForm.value="";
  softkeyboard.style.display="none";
  //Calc.password.value="";
 }
//显示软键盘
 function showkeyboard()
 {
  softkeyboard.style.display="block";
 }
//欢迎来到站长特效网，我们的网址是www.zzjs.net，很好记，zz站长，js就是js特效，本站收集大量高质量js代码，还有许多广告代码下载。
//设置是否大写的值
function setCapsLock()
{
 if (CapsLockValue==0)
 {
  CapsLockValue=1
  Calc.showCapsLockValue.value="当前是大写 ";
 }
 else
 {
  CapsLockValue=0
  Calc.showCapsLockValue.value="当前是小写 ";
 }
}//欢迎来到站长特效网，我们的网址是www.zzjs.net，很好记，zz站长，js就是js特效，本站收集大量高质量js代码，还有许多广告代码下载。
//-->