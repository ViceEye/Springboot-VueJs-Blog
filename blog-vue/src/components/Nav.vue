<template>
  <div class="header-container">
    <router-link to="/">
      <h2 class="site-title">Venja's Blog</h2>
    </router-link>
    <div class="breadcrumb">
     <el-breadcrumb class="breadcrumb-menu" separator="|">
        <el-breadcrumb-item><a href="/">首页</a></el-breadcrumb-item>
        <el-breadcrumb-item><a href="/">归档</a></el-breadcrumb-item>
        <el-breadcrumb-item><a href="/">动态</a></el-breadcrumb-item>
        <el-breadcrumb-item><a href="/">关于</a></el-breadcrumb-item>
      </el-breadcrumb>
    </div>
  </div>
</template>

<script>
export default {
  name: "Nav"
}
</script>

<style scoped>
a{
  text-decoration: none;
}
h2{
  color: black;
}
.header-container{
  height: 66px;
  margin-inside: 50px;
}
.site-title{
  float: left;
}
.breadcrumb{
  float: right;
  height: 100%;
  vertical-align: middle;
}
</style>