!function(a){var b=a.getElementsByTagName("script"),c=b[b.length-1],d=c.getAttribute("src").replace(/[^\/]+$/,"save.php");window.canvas2png=function(b){var c=b.tagName.toLowerCase();if("canvas"===c)if("undefined"!=typeof FlashCanvas)FlashCanvas.saveImage(b);else{var e=a.createElement("form"),f=a.createElement("input");e.setAttribute("action",d),e.setAttribute("method","post"),f.setAttribute("type","hidden"),f.setAttribute("name","dataurl"),f.setAttribute("value",b.toDataURL()),a.body.appendChild(e),e.appendChild(f),e.submit(),e.removeChild(f),a.body.removeChild(e)}}}(document);