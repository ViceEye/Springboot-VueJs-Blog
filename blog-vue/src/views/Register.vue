<template>
  <div class="register">
    <el-form class="form" v-loading="loading"
             element-loading-text="拼命加载中"
             element-loading-spinner="el-icon-loading"
             status-icon :model="registerForm" :rules="rules" ref="registerForm" label-width="80px" label-position="top">

      <h3>注册界面</h3>

      <el-form-item class="label" label="账号"  prop="username">
        <el-input class="item" v-model="registerForm.username">
        </el-input>
      </el-form-item>

      <el-form-item class="label" label="密码" prop="password">
        <el-input type="password" v-model="registerForm.password" autocomplete="off">
        </el-input>
      </el-form-item>

      <el-form-item class="label" label="确认密码" prop="repeatPassword">
        <el-input type="password" v-model="registerForm.repeatPassword" autocomplete="off">
        </el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit()">提交</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
    let validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.registerForm.repeatPassword !== '') {
          this.$refs.registerForm.validateField('repeatPassword');
        }
        callback();
      }
    };
    let validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.registerForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      registerForm: {
        username: 'Joka.Venja',
        password: '111111',
        repeatPassword: '111111',
      },
      rules: {
        username: [
          { required: true, message: "请输入账号", trigger: "blur" },
          { min: 3, max: 16, message: "长度在 3 到 16 个字符", trigger: "blur" }
        ],
        password: [
          { required: true, validator: validatePass },
          { min: 6, max: 32, message: "长度在 6 到 32 个字符", trigger: "blur" }
        ],
        repeatPassword: [
          { required: true, validator: validatePass2 },
          { min: 6, max: 32, message: "长度在 6 到 32 个字符", trigger: "blur" }
        ]
      },
      loading: false
    };
  },
  methods: {
    onSubmit() {
      this.loading = true;
      this.$refs['registerForm'].validate((valid) => {
        if (valid) {
          console.log('submit!');

          const _this = this;
          this.$axios.post("/register", this.registerForm).then(
              res => {
                const jwt = res.headers['authorization']
                const userInfo = res.data.data;

                _this.$store.commit("SET_TOKEN", jwt);
                _this.$store.commit("SET_USERINFO", userInfo);

                this.loading = false;
                _this.$router.push("/blogs");
              }
          ).catch(
              error => {
                if (error.message === "账号已存在") {
                  this.loading = false;
                }
              }
          )
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
  }
}
</script>

<style>
.register {
  margin: 0 auto;
  background-color: #bcdef3;
  height: 100vh;
  display: flex;
}
.form {
  align-self: center;
  margin: 0 auto;
  background-color: white;
  border-radius: 3px;
  padding: 3% 3%;
}
.label {
  text-align: left;
}
</style>