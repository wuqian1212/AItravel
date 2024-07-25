<template>
  <div style="position: relative;overflow: hidden;">
    <div class="wrapper">
      <img src="../components/icons/bg.png" alt="" class="left-img">
      <div class="name">
        <img src="../components/icons/logo.png" alt="Head Sculpture">
        <span>AI 旅游</span>
      </div>
      <!-- <div class="round"></div> -->
      <div class="form-wrapper">



        <div>
        <!-- 显示登录错误 -->
          <div v-if="loginError" class="error-message">{{ loginError }}</div>
        </div>



        <div class="login-form" v-if="!showRegistForm && !showForgotPwdForm">
          <h1 class="title">登录</h1>
          <p class="tips">
              没有账号？
            <span class="tips-span regist" @click="toggleForm('regist')">注册</span>
          </p>
          <div class="other-login">
            <div class="other-login-item" @click="loginWithWeChat">
              <img src="../components/icons/wechat.png" alt="" class="other-login-svg">
              <span>微信</span>
            </div>
            <div class="other-login-item" @click="loginWithQQ">
              <img src="../components/icons/dingding.png" alt="" class="other-login-svg2">
              <span>钉钉</span>
            </div>
          </div>
          <div class="diliver"><span>或</span></div>
          <div class="form-input">
            <div class="form-input-item">
              <p>用户名</p>
              <input type="text" class="ipt" v-model="loginForm.username">
              <img src="../components/icons/use.png" alt="" class="ipt-svg">
            </div>
            <div class="form-input-item">
              <p>密码</p>
              <input type="password" class="ipt" v-model="loginForm.password">
              <img src="../components/icons/code.png" alt="" class="ipt-svg">
            </div>
            <button class="btn" @click="login">登录</button>
          </div>
          <div class="forgot-pwd" @click="toggleForm('forgot')">忘记密码?</div>
        </div>
        <div class="regite-form" v-if="showRegistForm">
          <h1 class="title">注册</h1>
          <p class="tips">
              已经有账号了？
            <span class="tips-span sigin" @click="toggleForm('sigin')">登录</span>
          </p>
          <div class="form-input">
            <div class="form-input-item">
              <p>手机号</p>
              <input type="text" class="ipt" v-model="registForm.phone">
              <img src="../components/icons/phone.png" alt="" class="ipt-svg">
            </div>
            <div class="form-input-item">
              <p>用户名</p>
              <input type="text" class="ipt" v-model="registForm.username">
              <img src="../components/icons/use.png" alt="" class="ipt-svg">
            </div>
            <div class="form-input-item">
              <p>密码</p>
              <input type="password" class="ipt" v-model="registForm.password">
              <img src="../components/icons/code.png" alt="" class="ipt-svg">
            </div>
            <button class="btn" @click="regist">注册</button>
          </div>
          <div class="diliver"><span>或</span></div>
          <div class="other-login">
            <div class="other-login-item" @click="loginWithWeChat">
              <img src="../components/icons/wechat.png" alt="" class="other-login-svg">
              <span>微信</span>
            </div>
            <div class="other-login-item" @click="loginWithQQ">
              <img src="../components/icons/dingding.png" alt="" class="other-login-svg2">
              <span>钉钉</span>
            </div>
          </div>
        </div>
        <div class="forgot-pwd-form" v-if="showForgotPwdForm">
          <h1 class="title">忘记密码</h1>
          <p class="tips">
              我现在记得密码了？
            <span class="tips-span sigin" @click="toggleForm('sigin')">登录</span>
          </p>
          <div class="form-input">
            <div class="form-input-item">
              <p>邮箱</p>
              <input type="text" class="ipt" v-model="forgotPwdForm.email">
              <img src="../components/icons/email.png" alt="" class="ipt-svg">
            </div>
            <div class="form-input-item">
              <p>验证码</p>
              <input type="text" class="ipt" v-model="forgotPwdForm.code">
              <img src="../components/icons/code.jpg" alt="" class="ipt-svg">
              <span class="veri-code-tips" @click="sendVerificationCode">获取</span>
            </div>
            <div class="form-input-item">
              <p>密码</p>
              <input type="password" class="ipt" v-model="forgotPwdForm.password">
              <img src="../components/icons/code.png" alt="" class="ipt-svg">
            </div>
            <button class="btn" @click="changePassword">更改密码</button>
          </div>
        </div>
      </div>
      <div class="introduce">
        <div class="nav">
          <ul>
            <li>首页</li>
            <li>AI</li>
            <li>支持</li>
            <li>联系</li>
          </ul>
        </div>
      </div>
    </div>
  </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import axios from 'axios';
  
  const showRegistForm = ref(false);
  const showForgotPwdForm = ref(false);
  const loginError = ref('');  // To store login error messages
  
  const loginForm = ref({
    username: '',
    password: ''
  });
  
  const registForm = ref({
    phone: '',
    username: '',
    password: ''
  });
  
  const forgotPwdForm = ref({
    email: '',
    code: '',
    password: ''
  });
  
  const reckonTime = ref(null);
  const reckonTimeFlag = ref(5);
  
  function toggleForm(type) {
    switch (type) {
      case 'regist':
        showRegistForm.value = true;
        showForgotPwdForm.value = false;
        break;
      case 'forgot':
        showRegistForm.value = false;
        showForgotPwdForm.value = true;
        break;
      default:
        showRegistForm.value = false;
        showForgotPwdForm.value = false;
    }
    console.log('Toggle form:', type, showRegistForm.value, showForgotPwdForm.value);
  }
  
  async function login() {
    try {
      console.log('Attempting login with', loginForm.value);
      const response = await axios.post('/api/user/login', {
        username: loginForm.value.username,
        password: loginForm.value.password
      });
      console.log('Login action', response.data);
      if (response.data.success) {
        // Handle successful login (e.g., redirect to another page)
        loginError.value = '';  // Clear any previous error messages
      } else {
        // Display error message
        loginError.value = response.data.errorMsg;
      }
    } catch (error) {
      if (error.response) {
        console.error('Server Error:', error.response.data);
        loginError.value = 'An error occurred while communicating with the server.';
      } else if (error.request) {
        console.error('Network Error:', error.request);
        loginError.value = 'Network error. Please check your connection.';
      } else {
        console.error('Error:', error.message);
        loginError.value = 'An unexpected error occurred.';
      }
    }
  }
  
  async function regist() {
    try {
      console.log('Attempting register with', registForm.value);
      const response = await axios.post('/api/user/register', {
        phone: registForm.value.phone,
        username: registForm.value.username,
        password: registForm.value.password
      });
      console.log('Register action', response.data);
    } catch (error) {
      if (error.response) {
        console.error('Server Error:', error.response.data);
      } else if (error.request) {
        console.error('Network Error:', error.request);
      } else {
        console.error('Error:', error.message);
      }
    }
  }
  
  async function changePassword() {
    try {
      console.log('Attempting change password with', forgotPwdForm.value);
      const response = await axios.post('/api/user/change-password', {
        email: forgotPwdForm.value.email,
        code: forgotPwdForm.value.code,
        password: forgotPwdForm.value.password
      });
      console.log('Change Password action', response.data);
    } catch (error) {
      if (error.response) {
        console.error('Server Error:', error.response.data);
      } else if (error.request) {
        console.error('Network Error:', error.request);
      } else {
        console.error('Error:', error.message);
      }
    }
  }
  
  function sendVerificationCode() {
    console.log('Send verification code');
    reckonTime.value = setInterval(() => {
      if (reckonTimeFlag.value > 0) {
        reckonTimeFlag.value--;
      } else {
        clearInterval(reckonTime.value);
        reckonTimeFlag.value = 5;
      }
    }, 1000);
  }
  
  async function loginWithWeChat() {
    try {
      const response = await axios.post('/api/user/login-wechat');
      console.log('Login with WeChat', response.data);
    } catch (error) {
      if (error.response) {
        console.error('Server Error:', error.response.data);
      } else if (error.request) {
        console.error('Network Error:', error.request);
      } else {
        console.error('Error:', error.message);
      }
    }
  }
  
  async function loginWithQQ() {
    try {
      const response = await axios.post('/api/user/login-qq');
      console.log('Login with QQ', response.data);
    } catch (error) {
      if (error.response) {
        console.error('Server Error:', error.response.data);
      } else if (error.request) {
        console.error('Network Error:', error.request);
      } else {
        console.error('Error:', error.message);
      }
    }
  }
  </script>
  
  
<style scoped>
@font-face {
  font-family: 'CaslonIonic-Regular';
  font-weight: 700;
  src: url('./assets/font/CaslonIonic-Regular.otf') format('opentype');
  text-rendering: optimizeLegibility;
}

* {
  padding: 0;
  margin: 0;
}
html, body, #app {
  /* position: fixed; */
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
}

.wrapper {
  width: 100vw;
  height: 100vh;
  background-color: white;
  /* position: relative; */
  /* overflow: hidden; */
  /* display: flex; */
  justify-content: center;
  align-items: center;
  font-family: 'CaslonIonic-Regular', sans-serif;
}

.wrapper .left-img {
  width: 40%;
  position: absolute;
  top: 50%;
  left: 10%;
  transform: translateY(-50%);
}

/* .wrapper .round {
  width: 400px;
  height: 400px;
  background-color: #fff;
  border-radius: 50%;
  position: absolute;
  left: 40%;
  top: -300px;
  transform: translateX(-50%);
}

.wrapper .round::after {
  content: "";
  display: block;
  border: 1px solid rgb(194, 205, 216);
  width: 400px;
  height: 400px;
  border-radius: 50%;
  position: absolute;
  left: 80%;
  transform: translateX(-50%);
} */

.wrapper .name {
  position: absolute;
  left: 30px;
  top: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.wrapper .name img {
  width: 70px;
  height: 70px;
  border-radius: 50%;
  margin-right: 20px;
}

.wrapper .name span {
  font-size: 30px;
  font-weight: 900;
}

.wrapper .introduce {
  position: absolute;
  bottom: 20px;
  left: 30px;
  display: flex;
  flex-direction: column;
}

.wrapper .introduce .nav ul li {
float: left;
margin-right: 20px;
list-style: none;
font-size: 30px;
cursor: pointer;
transition: 0.3s;
}

.wrapper .introduce .nav ul li:hover {
color: rgb(87, 59, 255);
}

.wrapper .form-wrapper {
width: 500px;
position: absolute;
right: 15%;
top: 50%;
transform: translateY(-50%);
box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1), 0 6px 20px rgba(0, 0, 0, 0.19); 
background-color: #fff;
border: 0;
border-radius: 10px;
padding: 50px 60px;
}

.wrapper .form-wrapper .tips {
display: block;
margin: 20px 0;
}

.wrapper .form-wrapper .tips .tips-span {
color: rgb(87, 59, 255);
margin: 10px;
cursor: pointer;
}

.wrapper .form-wrapper .other-login {
width: 100%;
display: flex;
margin: 10px 0;
}

.wrapper .form-wrapper .other-login .other-login-item {
height: 40px;
flex: 1;
border: 1px solid rgb(194, 205, 216);
border-radius: 10px;
display: flex;
justify-content: center;
align-items: center;
cursor: pointer;
transition: 0.3s;
}

.wrapper .form-wrapper .other-login .other-login-item:nth-child(1) {
margin-right: 10px;
}

.wrapper .form-wrapper .other-login .other-login-item:nth-child(2) {
margin-left: 10px;
}

.wrapper .form-wrapper .other-login .other-login-item:hover {
border: 1px solid rgb(87, 59, 255);
}

.wrapper .form-wrapper .other-login .other-login-item .other-login-svg {
width: 40px;
height: 30px;
margin-right: 5px;
}

.wrapper .form-wrapper .other-login .other-login-item .other-login-svg2 {
width: 35px;
height: 35px;
margin-right: 5px;
}

.wrapper .form-wrapper .diliver {
width: 100%;
margin: 10px 0;
display: flex;
justify-content: center;
align-items: center;
}

.wrapper .form-wrapper .diliver span {
display: inline-block;
margin: 0 40px;
}

.wrapper .form-wrapper .diliver::before {
content: "";
display: block;
width: 100%;
height: 1px;
background-color: rgb(194, 205, 216);
}

.wrapper .form-wrapper .diliver::after {
content: "";
display: block;
width: 100%;
height: 1px;
background-color: rgb(194, 205, 216);
}

.wrapper .form-wrapper .form-input {
width: 100%;
margin: 10px 0;
}

.wrapper .form-wrapper .form-input .form-input-item {
width: 100%;
margin-bottom: 10px;
position: relative;
}

.wrapper .form-wrapper .form-input .form-input-item p {
font-size: 16px;
font-weight: 900;
margin-bottom: 5px;
}

.wrapper .form-wrapper .form-input .form-input-item .ipt {
width: 100%;
height: 45px;
border-radius: 10px;
border: 1px solid rgb(194, 205, 216);
padding: 15px 100px 15px 50px;
box-sizing: border-box;
margin-bottom: 5px 0;
outline: none;
font-size: 20px;
transition: 0.3s;
}

.wrapper .form-wrapper .form-input .form-input-item .ipt:hover {
border: 1px solid rgb(87, 59, 255);
}

.wrapper .form-wrapper .form-input .form-input-item .ipt:hover ~ .ipt-svg {
fill: rgb(87, 59, 255);
}

.wrapper .form-wrapper .form-input .form-input-item .ipt:focus {
border: 1px solid rgb(87, 59, 255);
}

.wrapper .form-wrapper .form-input .form-input-item .ipt:focus ~ .ipt-svg {
fill: rgb(87, 59, 255);
}

.wrapper .form-wrapper .form-input .form-input-item .ipt-svg {
position: absolute;
width: 25px;
height: 25px;
left: 10px;
top: 50%;
fill: #707070;
transition: 0.3s;
}

.wrapper .form-wrapper .form-input .form-input-item .veri-code-tips {
position: absolute;
right: 10px;
bottom: 10px;
cursor: pointer;
color: rgb(87, 59, 255);
}

.wrapper .form-wrapper .form-input .btn {
width: 100%;
height: 50px;
background-color: #000;
color: #fff;
border-radius: 10px;
border: 0;
margin: 10px 0;
font-size: 20px;
cursor: pointer;
font-family: 'CaslonIonic-Regular', sans-serif;
}

.wrapper .form-wrapper .forgot-pwd {
width: 100%;
text-align: center;
color: rgb(87, 59, 255);
margin: 10px 0;
cursor: pointer;
}

.wrapper .form-wrapper .login-form {
width: 100%;
height: 521px;
display: block;
}

.wrapper .form-wrapper .regite-form {
width: 100%;
height: 521px;
}

.wrapper .form-wrapper .forgot-pwd-form {
width: 100%;
height: 521px;
}

.rotate-vert-center {
animation: rotate-vert-center 0.5s cubic-bezier(0.455, 0.03, 0.515, 0.955) both;
}

@keyframes rotate-vert-center {
0% {
transform: translateY(-50%) rotate(0);
}
100% {
transform: translateY(-50%) rotate(360deg);
}
}
.error-message {
    color: red;
    font-weight: bold;
  }
</style>