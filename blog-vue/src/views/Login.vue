  <template>
  <div class="login">
    <el-form class="form" status-icon :rules="rules" :model="loginForm"  label-width="80px" ref="loginForm" label-position="top">

      <h3>登录界面</h3>

      <el-form-item class="label" label="账号" prop="username">
        <el-input class="item" v-model="loginForm.username">
        </el-input>
      </el-form-item>

      <el-form-item class="label" label="密码" prop="password">
        <el-input class="item" type="password" v-model="loginForm.password">
        </el-input>
      </el-form-item>

      <el-form-item>
        <el-button plain type="primary" @click="onSubmit">登录</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      loginForm: {
        username: '',
        password: '',
      },
      rules: {
        username: [
          { required: true, message: "请输入账号", trigger: "blur" },
          { min: 3, max: 16, message: "长度在 3 到 16 个字符", trigger: "blur" }
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 6, max: 32, message: "长度在 6 到 32 个字符", trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    onSubmit() {
      this.$refs["loginForm"].validate(valid => {
        if (valid) {
          console.log('submit!!');

          const _this = this;
          this.$axios.post("/login", this.loginForm).then(
              res => {
                const jwt = res.headers['authorization']
                const userInfo = res.data.data

                _this.$store.commit("SET_TOKEN", jwt);
                _this.$store.commit("SET_USERINFO", userInfo);

                _this.$router.push("/blogs");
              }
          )
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    }
  }
};
</script>

<style>
.login {
  background-color: #3d4450;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}
.form {
  min-width: 300px;
  margin-bottom: 20vh;
  background-color: white;
  border-radius: 3px;
  padding: 5% 3%;
}
.label{
  text-align: left;
}
</style>