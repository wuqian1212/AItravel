// pages/login/login.js
Page({
  data: {
    username: '',
    password: ''
  },

  onInputUsername(e) {
    this.setData({ username: e.detail.value });
  },

  onInputPassword(e) {
    this.setData({ password: e.detail.value });
  },
  
  onLogin() {
    const { username, password } = this.data;
    if (username && password) {
      wx.showLoading({ title: '登录中...' });
      wx.request({
        url: 'http://192.168.1.127:8080/user/login',  // 使用您的实际 API URL
        method: 'POST',
        data: {
          username: username,
          password: password  // 在实际应用中，应该在客户端对密码进行哈希处理
        },
        success: (res) => {
          wx.hideLoading();
          const { data, errorMsg, success } = res.data;
          if (success) {
            // 登录成功
            wx.setStorageSync('isLoggedIn', true);
            wx.setStorageSync('userInfo', { 
              id: data.id,
              username: data.username,
              header: data.header,
              phone: data.phone
              // 不存储密码
            });
            console.log(wx.getStorageSync('userInfo'))
            
            wx.switchTab({ url: '/pages/user/user' });
          } else {
            // 登录失败
            wx.showToast({
              title: errorMsg || '登录失败',
              icon: 'none'
            });
          }
        },
        fail: (err) => {
          wx.hideLoading();
          wx.showToast({
            title: '网络错误，请重试',
            icon: 'none'
          });
          console.error('Login error:', err);
        }
      });
    } else {
      wx.showToast({
        title: '请输入用户名和密码',
        icon: 'none'
      });
    }
  },
  goToRegister: function() {
    wx.navigateTo({
      url: '/pages/register/register'  // 注册页面的路径
    })
  }
  
});
