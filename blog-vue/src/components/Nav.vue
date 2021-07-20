<template>
  <div class="header-container-full" :class="{'mobile-nav-bar': true}">
    <router-link to="/">
      <h2 class="site-title">Venja's Blog</h2>
    </router-link>
    <div :class="{'mobile-hide': true}">
      <router-link to="/">
        <span class="nav-item" @mouseover="select(1)" @mouseout="clear()" :class="{selected: index === 1, highlight: highlight === 1}">
          <i class="el-icon-s-home"></i>首页
        </span>
      </router-link>
      <router-link to="/">
        <span class="nav-item" @mouseover="select(2)" @mouseout="clear()" :class="{selected: index === 2}">
          <i class="el-icon-s-management"></i>归档
        </span>
      </router-link>
      <router-link to="/">
        <span class="nav-item" @mouseover="select(3)" @mouseout="clear()" :class="{selected: index === 3}">
          <i class="el-icon-message-solid"></i>动态
        </span>
      </router-link>
      <router-link to="/">
        <span class="nav-item" @mouseover="select(4)" @mouseout="clear()" :class="{selected: index === 4}">
          <i class="el-icon-info"></i>关于
        </span>
      </router-link>
    </div>
    <el-dropdown class="nav-avatar" @command="goto">
      <span class="el-dropdown-link">
        <el-avatar :size="40" :src="user.avatar"></el-avatar>
      </span>
      <el-dropdown-menu slot="dropdown">
        <p class="nav-avatar-username"> {{user.username}} </p>
        <el-dropdown-item :class="{'mobile-show': true}" icon="el-icon-s-home" command="/">首页</el-dropdown-item>
        <el-dropdown-item v-show="canEdit && isLogon" icon="el-icon-edit" :command=this.editPath>编辑</el-dropdown-item>
        <el-dropdown-item v-show="!canEdit && isLogon && notEdit" icon="el-icon-edit" command="/blog/add">发表</el-dropdown-item>
        <el-dropdown-item :class="{'mobile-show': true}" icon="el-icon-s-management">归档</el-dropdown-item>
        <el-dropdown-item :class="{'mobile-show': true}" icon="el-icon-message-solid">动态</el-dropdown-item>
        <el-dropdown-item :class="{'mobile-show': true}" icon="el-icon-info">关于</el-dropdown-item>
        <el-dropdown-item v-show="!isLogon" icon="el-icon-user" command="/login">登录</el-dropdown-item>
        <el-dropdown-item v-show="isLogon" icon="el-icon-circle-close" command="logout">退出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>

  </div>
</template>

<script>
export default {
  name: "Nav",
  data() {
    return {
      user: {
        avatar: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
        username: '请先登录'
      },
      isLogon: false,
      canEdit: false,
      notEdit: this.$route.path !== '/blog/add',
      editPath: '-1',
      highlight: 0,
      list: [1, 2, 3, 4, 5],
      index: 0
    }
  },
  created() {
    this.user.username = '请先登录';
    this.user.avatar = 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png';

    switch (this.$route.path)
    {
      case "/":
        this.highlight = 1;
        break;
      case "/archives":
        this.highlight = 2;
        break;
      case "/moments":
        this.highlight = 3;
        break;
      case "/about":
        this.highlight = 4;
        break;
    }

    this.isLogon = false;
    if (this.$store.getters.getToken) {
      this.$axios.post("/remember", {}, {
        headers: {
          "authorization": this.$store.getters.getToken
        }
      }).then(
          res => {
            if (res.data.data === "Failed") {
              this.$store.commit("REMOVE_INFO");
            } else {
              this.isLogon = true;
              const userInfo = res.data.data;
              this.$store.commit("SET_USERINFO", userInfo);
            }
          }
      )
    } else {
      this.$store.commit("REMOVE_INFO");
    }

    const sessionUserInfo = this.$store.getters.getUser;
    if (sessionUserInfo.username != null && sessionUserInfo.avatar != null) {
      this.user.username = sessionUserInfo.username;
      this.user.avatar = sessionUserInfo.avatar;
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
  },
  methods: {
    goto(href) {
      if (href !== "logout") {
        this.$router.push(href);
      } else {
        this.logout();
      }
    },
    select(i) {
      this.index = i;
    },
    clear() {
      this.index = 0;
    },
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
}
</script>

<style scoped>
.header-container-full{
  width: 50%;
  padding: 0 25%;
  display: flex;
  background-color: #3d4450;
}
h2{
  color: white;
  margin: 10px 50px 10px 0 !important;
}
.site-title{
  margin: 0;
  float: left;
}
.nav-item{
  color: white;
  float: left;
  margin: 0 2px;
  padding: 16px !important;
  font-size: 13px;
}
.nav-avatar{
  margin: 3px 0 3px auto;
}
.nav-avatar-username{
  color: #606266;
  font-size: 14px;
  margin: 0 auto;
  padding: 0 20px;
  line-height: 36px;
  text-decoration: underline;
  user-select: none;
}
.selected {
  background-color: #262c35;
}
.highlight {
  background-color: #d9534f;
}
.el-link {
  width: 100%;
  height: 100%;
}
.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}
</style>