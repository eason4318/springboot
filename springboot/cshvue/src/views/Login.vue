<template>
  <div class="background" >
    <div style="margin: 150px auto; width: 350px; height: 300px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 32px; color: cyan"><b>欢 迎 登 录</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item style="margin: 10px 0px;">
          <el-button style="width: 140px; margin-right: 20px" type="primary" size="big"  autocomplete="off" @click="login">登录</el-button>
          <el-button style="width: 140px;" type="warning" size="big"  autocomplete="off" @click="$router.push('register')">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
 </div>

</template>

<script>
import  {setRoutes} from "@/router";

export default {
  name: "Login",
  data() {
    return {
      background: {
        backgroudImage: 'url(' + require('../assets/11.png') + ')',

      },
      user: {},
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    login() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          this.request.post("/user/login", this.user).then(res => {
            if(res.code === '200') {
              localStorage.setItem("user", JSON.stringify(res.data))  // 存储用户信息到浏览器
              localStorage.setItem("menus", JSON.stringify(res.data.menus))  // 存储用户信息到浏览器
              setRoutes()
              this.$router.push("/")
              this.$message.success("登录成功")
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      });
    },
  }
}
</script>

<style>
.background {
  background-image: url(../assets/11.png);
  /* 加了no-repeat反而没有背景图片出现 */
  background-position: center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: fixed;
}
/*.wrapper {*/
/*  height:100vh;*/
/*  background-image: linear-gradient(to bottom right, #FC466B , #3F5EFB);*/
/*  overflow: hidden;*/
/*}*/
/*.background{*/
/*  width:100%;*/
/*  height:100%;  !**宽高100%是为了图片铺满屏幕 *!*/
/*  z-index:-1;*/
/*  position: absolute;*/
/*}*/

/*.wrapper{*/
/*  z-index:1;*/
/*  position: absolute;*/
/*  height: 100vh;*/
/*  overflow: hidden;*/
/*}*/
</style>
