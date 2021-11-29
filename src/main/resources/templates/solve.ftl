<!DOCTYPE html>
<html>
<body>

<canvas id="myCanvas" width="644" height="644">
    Your browser does not support the canvas element.
</canvas>
<#-- block size 92-->
<script>
    var canvas = document.getElementById("myCanvas");
    var ctx = canvas.getContext("2d");
    ctx.fillStyle = "#FE0000";
    <#list solution[0] as block>
        ctx.fillRect(${block.x*92},${block.y*92},92,92);
    </#list>
    ctx.fillStyle = "#FFFF00";
    <#list solution[1] as block>
        ctx.fillRect(${block.x*92},${block.y*92},92,92);
    </#list>
    ctx.fillStyle = "#7DFF00";
    <#list solution[2] as block>
        ctx.fillRect(${block.x*92},${block.y*92},92,92);
    </#list>
    ctx.fillStyle = "#0000FF";
    <#list solution[3] as block>
        ctx.fillRect(${block.x*92},${block.y*92},92,92);
    </#list>
    ctx.fillStyle = "#AD10D4";
    <#list solution[4] as block>
        ctx.fillRect(${block.x*92},${block.y*92},92,92);
    </#list>
    ctx.fillStyle = "#FF4001";
    <#list solution[5] as block>
        ctx.fillRect(${block.x*92},${block.y*92},92,92);
    </#list>
    ctx.fillStyle = "#5A3E1C";
    <#list solution[6] as block>
        ctx.fillRect(${block.x*92},${block.y*92},92,92);
    </#list>
    ctx.fillStyle = "#000000";
    <#list solution[7] as block>
        ctx.fillRect(${block.x*92},${block.y*92},92,92);
    </#list>
</script>

</body>
</html>