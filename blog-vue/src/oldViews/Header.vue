<template>
  <div class="header">
    <h3>欢迎来到Venja的博客</h3>

    <div>
      <el-avatar :size="50" :src="user.avatar"></el-avatar>
      <div>{{ user.username }}</div>
    </div>

    <div class="nav">
      <span>
        <el-link type="primary" icon="el-icon-s-home" href="/">主页</el-link>
      </span>
      <el-divider direction="vertical"></el-divider>
      <span v-show="canEdit">
        <el-link type="success" icon="el-icon-edit" :href="editPath">编辑</el-link>
      </span>
      <span v-show="!canEdit">
        <el-link type="success" icon="el-icon-edit" href="/blog/add">发表</el-link>
      </span>
      <el-divider direction="vertical"></el-divider>
      <span v-show="!hasLogin">
        <el-link type="warning" icon="el-icon-user" href="/login">登录</el-link>
      </span>
      <span v-show="hasLogin">
        <el-link type="danger" icon="el-icon-circle-close" @click="logout">退出</el-link>
      </span>
    </div>

  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      user: {
        username: '请先登录',
        avatar: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
      },
      hasLogin: false,
      canEdit: false,
      editPath: '-1',
    }
  },
  methods: {
    logout() {
      const _this = this;
      _this.$axios.get("/logout", {
        headers: {
          "Authorization": this.$store.getters.getToken
        }
      }).then(
          res => {
            _this.$store.commit("REMOVE_INFO");
            _this.$router.push("/login");
          }
      )
    }
  },
  created() {
    if (this.$store.getters.getUser) {
      this.user.username = this.$store.getters.getUser.username;
      this.user.avatar = this.$store.getters.getUser.avatar;

      this.hasLogin = true;
    }

    const blogId = this.$route.params.blogId;
    const _this = this;
    if (blogId) {
      this.$axios.get('/blog/' + blogId).then(
          res => {
            const blog = res.data.data;

            _this.editPath = "/blog/" + blog.id + "/edit";
            _this.canEdit = (blog.userId === (_this.$store.getters.getUser) ? _this.$store.getters.getUser.id : -1);
          }
      )
    }
  }
}
</script>

<style scoped>
.header{
  display: inline-block;
  background-color: #bcdef3;
  width: 100%;
}
.nav{
  margin: 10px;
}
</style>