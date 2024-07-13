// pages/register/register.js
Page({
  data: {
    username: '',
    password: '',
    confirmPassword: ''
  },

  onInputUsername(e) {
    this.setData({
      username: e.detail.value
    });
  },

  onInputPassword(e) {
    this.setData({
      password: e.detail.value
    });
  },

  onInputConfirmPassword(e) {
    this.setData({
      confirmPassword: e.detail.value
    });
  },

  onRegister() {
    const { username, password, confirmPassword } = this.data;

    // 基本的表单验证
    if (!username || !password || !confirmPassword) {
      wx.showToast({
        title: '请填写所有字段',
        icon: 'none'
      });
      return;
    }

    if (password !== confirmPassword) {
      wx.showToast({
        title: '两次输入的密码不一致',
        icon: 'none'
      });
      return;
    }

    // 显示加载提示
    wx.showLoading({
      title: '注册中...',
    });

    // 调用注册 API
    wx.request({
      url: 'http://192.168.1.127:8080/user/register', // 替换为您的实际 API 地址
      method: 'POST',
      data: {
        username: username,
        password: password
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
          wx.showToast({
            title: res.data.message || '注册失败，请重试',
            icon: 'none'
          });
        }
      },
      fail: (error) => {
        wx.hideLoading();
        wx.showToast({
          title: '网络错误，请重试',
          icon: 'none'
        });
        console.error('Registration failed:', error);
      }
    });
  },

  goToLogin() {
    wx.navigateBack();
  }
});
