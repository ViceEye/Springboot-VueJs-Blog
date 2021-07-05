<template>
  <div>
    <Nav></Nav>

    <el-container>
      <el-main
          v-loading="loading"
          element-loading-text="拼命加载中"
          element-loading-spinner="el-icon-loading"
          class="blogs">

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
      _this.$axios.get("/blogs?currentPage=" + currentPage, {
        headers: {
          "Authorization": localStorage.getItem("token")
        }
      }).then(
          res => {
            _this.blogs = res.data.data.records;
            _this.currentPage = res.data.data.current;
            _this.totalPage = res.data.data.total;
            _this.pageSize = res.data.data.size;
            _this.loading = false;
          }
      )
    }
  },
  created() {
    this.page(1);
  }
}
</script>

<style scoped>
.site {
  display: flex;
  min-height: 100vh; /* 没有元素时，也把页面撑开至100% */
  flex-direction: column;
}

.main {
  margin-top: 40px;
  flex: 1;
}

.main .ui.container {
  width: 1400px !important;
  margin-left: auto !important;
  margin-right: auto !important;
}

.ui.grid .three.column {
  padding: 0;
}

.ui.grid .ten.column {
  padding-top: 0;
}

.m-position-sticky {
  position: sticky !important;
  top: 68px;
}

.m-display-none {
  display: none !important;
}
</style>