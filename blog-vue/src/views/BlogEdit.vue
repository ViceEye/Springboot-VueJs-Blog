<template>
  <div>
    <Nav></Nav>

    <div class="edit-content" :class="{'mobile-90': true, 'mobile-80': true}">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" label-position="top" hide-required-asterisk>
        <el-form-item label="主题" prop="title">
          <el-input v-model="ruleForm.title"></el-input>
        </el-form-item>
        <el-form-item label="摘要" prop="description">
          <el-input type="textarea" v-model="ruleForm.description"></el-input>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-tiptap
              v-model="ruleForm.content"
              :extensions="extensions"
              placeholder="开始编辑..."
          />
        </el-form-item>
        <el-form-item class="buttons">
          <el-button :loading="sending" type="primary" @click="submitForm('ruleForm')">立即发表</el-button>
          <el-button :loading="sending" @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>

    </div>

    <Footer></Footer>
  </div>
</template>

<script>
  import Nav from "../components/Nav";
  import Footer from "../components/Footer";

  import {
    Doc,
    Text,
    Paragraph,
    Heading,
    TextColor,
    Bold,
    Underline,
    Italic,
    Strike,
    FontType,
    FontSize,
    TextHighlight,
    LineHeight,
    Link,
    Blockquote,
    CodeBlock,
    TextAlign,
    ListItem,
    BulletList,
    OrderedList,
    Indent,
    HorizontalRule,
    FormatClear,
    History,
    Preview,
    Fullscreen,
  } from 'element-tiptap';

  export default {
    name: "BlogEdit",
    components: {
      Nav,
      Footer,
    },
    data() {
      return {
        extensions: [
          new Doc(),
          new Text(),
          new Paragraph(),
          new Heading({ level: 5 }),
          new TextColor( { bubble: true } ),
          new Bold( { bubble: true } ),
          new Underline( { bubble: true } ),
          new Italic( { bubble: true } ),
          new Strike( { bubble: true } ),
          new FontType,
          new FontSize,
          new TextHighlight({ bubble: true, menubar: false }),
          new LineHeight(),
          new Link(),
          new Blockquote(),
          new CodeBlock(),
          new TextAlign( { bubble: true, menubar: false } ),
          new ListItem(),
          new BulletList(),
          new OrderedList(),
          new Indent(),
          new HorizontalRule(),
          new FormatClear(),
          new History(),
          new Preview(),
          new Fullscreen(),
        ],
        // 编辑器的内容
        ruleForm: {
          id: '',
          title: '',
          description: '',
          content: ''
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
        sending: false,
      };
    },
    methods: {
      submitForm(formName) {
        this.sending = true;
        this.$refs[formName].validate((valid) => {
          if (valid) {

            const _this = this;

            //this.ruleForm.content = this.ruleForm.content.replace("<p data-f-id=\"pbf\" style=\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\">Powered by <a href=\"https://www.froala.com/wysiwyg-editor?pb=1\" title=\"Froala Editor\">Froala Editor</a></p>", "");
            _this.$axios.post('/blog/edit', this.ruleForm, {
              headers: {
                "Authorization": this.$store.getters.getToken
              }
            }).then(
                res => {
                  _this.$alert('发表成功', '来自Venja\'s Blog的提示' , {
                    confirmButtonText: '确定',
                    callback: action => {
                      _this.$router.push("/");
                    }
                  });
                }
            )

          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    },
    created() {
      const blogId = this.$route.params.blogId;
      const _this = this;
      _this.sending = false;
      if (blogId) {
        this.$axios.get('/blog/' + blogId).then(
            res => {
              const blog = res.data.data;
              _this.ruleForm.id = blog.id;
              _this.ruleForm.title = blog.title;
              _this.ruleForm.description = blog.description;
              _this.ruleForm.content = blog.content;
            }
        )
      }
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
</style>