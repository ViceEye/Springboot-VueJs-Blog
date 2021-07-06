<template>
  <div>
    <Nav></Nav>

    <el-container class="blog-container">
      <el-main
          v-loading="loading"
          element-loading-text="拼命加载中"
          element-loading-spinner="el-icon-loading"
          class="blog-blog-list">

        <div v-for="blog in blogs">
          <BlogItem :blog=blog />
        </div>

        <el-pagination
            background
            layout="prev, pager, next"
            :current-page="currentPage"
            :page-size="pageSize"
            :total="totalPage"
            @current-change="page"
        >
        </el-pagination>

      </el-main>
    </el-container>

    <Footer></Footer>
  </div>
</template>

<script>
import Nav from "../components/Nav";
import BlogItem from "../components/BlogItem";
import Footer from "../components/Footer";
export default {
  name: "TestBlogs",
  components: {
    Nav,
    BlogItem,
    Footer,
  },
  data() {
    return {
      blogs: {},
      currentPage: 1,
      totalPage: 0,
      pageSize: 5,
      loading: true,
    }
  },
  methods: {
    page(currentPage) {
      const _this = this;
      if (this.$store.getters.getToken) {
        _this.getRecords(currentPage, this.$store.getters.getToken);
      } else {
        _this.getRecords(currentPage, "");
      }
    },
    getRecords(currentPage, token) {
      const _this = this;
      if (token === "") {
        _this.$axios.get("/blogs?currentPage=" + currentPage).then(
            res => {
              this.loadRes(res);
            }
        )
      } else {
        _this.$axios.get("/blogs?currentPage=" + currentPage, {
          headers: {
            "Authorization": token
          }
        }).then(
            res => {
              if (res.data.data != null) {
                this.loadRes(res);
              } else {
                _this.getRecords(currentPage, "");
              }
            }
        )
      }
    },
    loadRes(res) {
      const _this = this;
      _this.blogs = res.data.data.records;
      _this.currentPage = res.data.data.current;
      _this.totalPage = res.data.data.total;
      _this.pageSize = res.data.data.size;
      _this.loading = false;
    }
  },
  created() {
    this.page(1);
  }
}
</script>

<style scoped>
.blog-container {
  margin: 0 0 20px 0;
}
</style>