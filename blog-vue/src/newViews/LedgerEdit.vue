<template>
  <div>
    <Nav></Nav>

    <!--
    <span class="ledger-edit-out t2 bold" v-on:click="test1">支出</span>
    <span class="ledger-edit-in t5 bold">收入</span>
    -->

    <div class="edit-content" :class="{'mobile-90': true, 'mobile-80': true}">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="40px" label-position="left" hide-required-asterisk>
        <el-col :span="12">
          <el-form-item style="margin-right: 10px;" label="类型">
            <el-select style="width: 100%;" v-model="ruleForm.type" placeholder="请选择记录类型">
              <el-option label="支出" value=3></el-option>
              <el-option label="收入" value=2></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item style="margin-left: 10px;" label="金额" prop="title">
            <el-input v-model.number="ruleForm.amount"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="备注" prop="title">
            <el-input v-model="ruleForm.note"></el-input>
          </el-form-item>
          <el-form-item label="详细" prop="description">
            <el-input type="textarea" :autosize="{ minRows: 4, maxRows: 4}" v-model="ruleForm.comment"></el-input>
          </el-form-item>
          <el-form-item class="buttons">
            <el-button :loading="sending" type="primary" @click="submitForm('ruleForm')">立即发表</el-button>
            <el-button :loading="sending" @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-col>
      </el-form>
    </div>
    <el-col :span="24">
      {{name}}
      <IconSelector mode="add" :type=3 :pass-icon=icons></IconSelector>
    </el-col>

    <Footer></Footer>
  </div>
</template>

<script>
import Nav from "../components/Nav";
import Footer from "../components/Footer";
import IconSelector from "./IconSelector";
export default {
  name: "LedgerAdd",
  components: {
    IconSelector,
    Nav,
    Footer,
  },
  data() {
    return {
      name: 'test',
      show: false,
      ruleForm: {
        type: "",
        amount: 0,
        note: "",
        comment: "",
        dateTime: "",
      },
      rules: {
        title: [
          { required: true, message: '请输入文章主题', trigger: 'blur' },
          { min: 2, max: 20, message: '长度至少 2 个字符', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入此篇文章的简介', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '请输入你想发表的文章内容', trigger: 'blur' }
        ]
      },
      types:[{
        value: '2',
        label: '收入'
      }, {
        value: '3',
        label: '支出'
      }],
      sending: false,
      icons: Array,
    }
  },
  methods: {
    setName(name) {
      this.name = name;
    },
    setType(type) {
      this.ruleForm.type = this.types[type].label;
    },
    getType() {
      if (this.ruleForm.type === "支出") {
        return 3;
      }
      if (this.ruleForm.type === "收入") {
        return 2;
      }
    }
  },
  mounted() {
    this.$EventBus.$on("icon_clicked", (val) => {
      this.setName(val);
    })
  },
  created() {
    this.setType(1);
    let labels = this.$axios.get("/getAllLabel?type=" + this.getType(), {
      headers: {
        "Authorization": this.$store.getters.getToken
      }
    });
    console.log(labels)
  }
}
</script>

<style scoped>
.edit-content{
  width: 60%;
  margin: 40px auto;
  text-align: left;
  font-family: "Avenir", Helvetica, Arial, sans-serif;
}
.buttons{
  text-align: center;
}
.ledger-edit-out {
  padding: 5px;
  user-select: none;
}
.ledger-edit-in {
  user-select: none;
}
</style>