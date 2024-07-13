// pages/changePhone/changePhone.js
Page({
  data: {
    newPhone: '',
    userId: '' // 假设用户ID存储在这里
  },

  onLoad: function(options) {
    // 页面加载时,可以从options或其他地方获取userId
    this.setData({
      userId: wx.getStorageSync('userInfo').id
    });
  },

  inputPhone: function(e) {
    this.setData({
      newPhone: e.detail.value
    });
  },

  // 新增：验证电话号码格式的函数
  validatePhoneNumber: function(phoneNumber) {
    const phoneRegex = /^1[3-9]\d{9}$/;
    return phoneRegex.test(phoneNumber);
  },

  submitNewPhone: function() {
    const { newPhone, userId } = this.data;
    
    if (!newPhone) {
      wx.showToast({
        title: '请输入新的电话号码',
        icon: 'none'
      });
      return;
    }

    // 验证电话号码格式
    if (!this.validatePhoneNumber(newPhone)) {
      wx.showToast({
        title: '请输入有效的手机号码',
        icon: 'none'
      });
      return;
    }

// 确保 userId 是数字类型
const idEnd = parseInt(userId, 10);
    // 首先，确保 userId 和 newPhone 是正确的值
console.log('当前 userId:', idEnd);
console.log('新电话号码:', newPhone);


const requestData = {
  id: idEnd,
  phone: newPhone
};

console.log('发送的请求数据:', requestData);

wx.request({
  url: 'http://192.168.1.127:8080/user/updatePhone',
  method: 'PUT',
  data: requestData,
  header: {
    'content-type': 'application/json' // 确保设置正确的内容类型
  },
  success: (res) => {
    console.log('服务器响应:', res.data);
    if (res.data.success) {
      wx.showToast({
        title: '电话修改成功',
        icon: 'success'
      });
      // 可以在这里执行其他操作，比如刷新页面数据
      // this.refreshUserData();
    } else {
      wx.showToast({
        title: res.data.message || '修改失败,请重试',
        icon: 'none'
      });
    }
  },
  fail: (error) => {
    console.error('请求失败', error);
    wx.showToast({
      title: '网络错误,请重试',
      icon: 'none'
    });
  },
  complete: () => {
    // 无论成功失败都会执行
    console.log('请求完成');
    // 这里可以添加一些清理工作或者刷新操作
  }
});

// 可以添加一个刷新用户数据的函数
function refreshUserData() {
  // 这里添加重新获取用户数据的逻辑
  console.log('刷新用户数据');
  // 例如：重新调用获取用户信息的API
}

  }
});
