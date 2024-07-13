// pages/changePassword/changePassword.js
Page({
  data: {
    userId: '',
    oldPassword: '',
    newPassword: '',
    confirmPassword: '',
    oldPasswordError: false // 新增：用于控制旧密码输入框的样式
  },

  onLoad: function(options) {
    this.setData({
      userId: wx.getStorageSync('userInfo').id  
    });
    console.log(wx.getStorageSync('userInfo').id);
  },

  inputOldPassword: function(e) {
    this.setData({ 
      oldPassword: e.detail.value,
      oldPasswordError: false // 重置错误状态
    });
  },

  inputNewPassword: function(e) {
    this.setData({ newPassword: e.detail.value });
  },

  inputConfirmPassword: function(e) {
    this.setData({ confirmPassword: e.detail.value });
  },

  validateOldPassword: function() {
    const { userId, oldPassword } = this.data;
    console.log('发送请求前的 userId:', userId);
    const requestPassword = {
      id: userId,
      password: oldPassword
    };
    return new Promise((resolve, reject) => {
      wx.request({
        url: 'http://192.168.1.127:8080/user/checkPassword',
        method: 'POST',
        data: requestPassword,
        header: {
          'content-type': 'application/json'
        },
        success: (res) => {
          console.log(requestPassword)
          if (res.data.success) {
            resolve(true);
          } else {
            reject('原密码不正确');
            console.log(res.data);
          }
        },
        fail: (error) => {
          console.error('网络请求失败', error);
          reject('网络错误，请重试');
        }
      });
    });
  },

  changePassword: function() {
    // 首先验证旧密码
    this.validateOldPassword().then(() => {
      // 旧密码验证成功，继续修改密码的流程
      const { newPassword, confirmPassword, userId } = this.data;
      
      // 验证新密码和确认密码是否一致
      if (newPassword !== confirmPassword) {
        wx.showToast({
          title: '新密码和确认密码不一致',
          icon: 'none'
        });
        return;
      }

      // 确保 userId 是数字类型
      const idEnd = parseInt(userId, 10);
      
      const requestData = {
        id: idEnd,
        password: newPassword
      };

      wx.request({
        url: 'http://192.168.1.127:8080/user/updatePassword',
        method: 'PUT',
        data: requestData,
        header: {
          'content-type': 'application/json'
        },
        success: (res) => {
          console.log('服务器响应:', res.data);
          if (res.data.success) {
            wx.showToast({
              title: '密码修改成功',
              icon: 'success'
            });
            // 可以在这里执行其他操作，比如返回上一页
            // wx.navigateBack();
          } else {
            wx.showToast({
              title: '密码修改失败',
              icon: 'none'
            });
          }
        },
        fail: (error) => {
          console.error('请求失败', error);
          wx.showToast({
            title: '网络错误，请重试',
            icon: 'none'
          });
        }
      });
    }).catch((error) => {
      // 旧密码验证失败
      this.setData({ oldPasswordError: true });
      wx.showToast({
        title: '原密码输入错误',
        icon: 'none',
        duration: 3000
      });
      // 3秒后重置错误状态
      setTimeout(() => {
        this.setData({ oldPasswordError: false });
      }, 3000);
    });
  }
});

