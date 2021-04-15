<template>
  <div class="footer">
    Copyright © 2021 <a href="/">Venja.</a> <a href="https://beian.miit.gov.cn/" rel="nofollow" target="_blank">闽ICP备2021003209号</a>
  </div>
</template>

<script>
export default {
  name: "Footer"
}
</script>

<style scoped>
.footer{
  width: 100%;
  position: fixed;
  bottom: 10px;
}
a:link{
  text-decoration: none;
  color: black;
}
a:visited{
  color: black;
}
a:active{
  color: black;
}
a:hover{
  color: red;
}
</style>