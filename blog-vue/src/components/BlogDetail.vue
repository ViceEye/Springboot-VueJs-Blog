<template>
  <div>
    <Nav></Nav>

    <el-container v-loading="loading"
                  element-loading-text="拼命加载中"
                  element-loading-spinner="el-icon-loading"
                  class="blog">
      <el-main>
        <h2 class="blog-title"> {{ blog.title }} </h2>

        <el-divider></el-divider>

        <div class="blog-content" v-html="blog.content"></div>
      </el-main>
    </el-container>

    <Footer></Footer>
  </div>
</template>

<script>
import Header from "./Header";
import Footer from "./Footer";
import Nav from "./Nav";

export default {
  name: "BlogDetail",
  components: {Nav, Header, Footer},
  data() {
    return {
      blog: {
        id: "",
        title: "",
        content: "",
      },
      loading: true
    }
  },
  created() {
    const blogId = this.$route.params.blogId;
    const _this = this;
    if (blogId) {
      this.$axios.get('/blog/' + blogId).then(
          res => {
            const blog = res.data.data;

            _this.blog.id = blog.id;
            _this.blog.title = blog.title;

            //let MarkdownIt = require("markdown-it");
            //let md = new MarkdownIt();

            _this.blog.content = blog.content;
            _this.loading = false;
          }
      )
    }
  }
}
</script>

<style scoped>
.blog{
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  width: 60%;
  min-height: 700px;
  margin: 40px auto;
  padding: 20px 40px;
}
.blog-content{
  text-align: left;
}
</style>