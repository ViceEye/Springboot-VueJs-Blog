<template>
  <div>
    <!--<Nav></Nav>-->
    <Header></Header>

    <el-container>
      <el-main
          v-loading="loading"
          element-loading-text="拼命加载中"
          element-loading-spinner="el-icon-loading"
          class="blogs">
        <el-timeline>

          <el-timeline-item class="timestamp" :timestamp="blog.created" placement="top" v-for="blog in blogs">
            <el-card class="blog-content">
              <router-link class="blog-title" :to="{name: 'BlogDetail', params: {blogId: blog.id}}">
                <h4>{{ blog.title }}</h4>
              </router-link>
              <p>{{ blog.description }}</p>
            </el-card>
          </el-timeline-item>

        </el-timeline>

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
  import Header from "../components/Header";
  import Footer from "../components/Footer";
  export default {
    name: "Blogs.vue",
    components: {
      Header,
      Footer,
      Nav,
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
              console.log(res);
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
.blogs{
  max-width: 960px;
  margin: 40px auto;
}
.timestamp{
  text-align: left;
}
.blog-title{
  color: black;
  text-decoration: none;
}
</style>