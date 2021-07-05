<template>
  <div class="blog-item-box" :class="{'mobile-full': true}">
    <el-col class="blog-item-container">
    </el-col>
      <el-card class="box-card" shadow="hover" :body-style="{ padding: '20px 0px 25px 0px' }">
        <h2> {{ blog.title }} </h2>
        <div>
          <i class="el-icon-date box-card-info blue"> {{ blog.created }} </i>
          <i class="el-icon-thumb box-card-info yellow">≈666</i>
        </div>
        <!--
        <el-image
            style="width: 100%; height: 350px"
            src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
            fit="contain">
        </el-image>
        -->
        <p class="box-card-description"> {{ blog.description }} </p>
        <router-link class="blog-title" :to="{name: 'BlogDetail', params: {blogId: blog.id}}">
            <el-button round type="danger">阅读全文</el-button>
        </router-link>
      </el-card>
  </div>
</template>

<script>
export default {
  name: "BlogItem",
  props: {
    blog: {
      type: Object,
      required: true
    }
  }
}
</script>

<style scoped>
h2 {
  margin: 0;
  padding: 0;
}
.blog-item-box {
  margin: 0 auto;
  width: 800px;
}
.blog-item-container {
  width: 500px;
}
.box-card {
  margin: 20px;
}
.box-card-description {
  text-align: left;
  padding: 0 40px 10px 40px;
}
.box-card-info {
  font-size: 13px;
  padding: 10px 18px 10px;
}
</style>