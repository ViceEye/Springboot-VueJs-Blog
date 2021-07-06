<template>
  <div>
    <Nav></Nav>

    <el-container v-loading="loading"
                  element-loading-text="拼命加载中"
                  element-loading-spinner="el-icon-loading"
                  class="blog"
                  :class="{'mobile-80': true, 'mobile-100': true}">
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
import Footer from "./Footer";
import Nav from "./Nav";

export default {
  name: "BlogDetail",
  components: {Nav, Footer},
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
  methods: {
    getBlog(blogId, token) {
      const _this = this;
      if (token === "") {
        _this.$axios.get('/blog/' + blogId).then(
            res => {
              _this.loadRes(res)
            }
        )
      } else {
        _this.$axios.get('/blog/' + blogId, {
          headers: {
            "Authorization": token
          }
        }).then(
            res => {
              _this.loadRes(res)
            }
        )
      }
    },
    loadRes(res) {
      const _this = this;
      const blog = res.data.data;

      _this.blog.id = blog.id;
      _this.blog.title = blog.title;

      _this.blog.content = blog.content;
      _this.loading = false;
    }
  },
  created() {
    const blogId = this.$route.params.blogId;
    if (blogId) {
      const _this = this;
      if (this.$store.getters.getToken) {
        _this.getBlog(blogId, this.$store.getters.getToken);
      } else {
        _this.getBlog(blogId, "");
      }
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