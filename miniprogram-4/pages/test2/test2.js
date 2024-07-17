// pages/record/record.js  
Page({  
  data: {  
    isRecording: false,  
    hasRecorded: false,  
    recordedFilePath: ''  
  },  
  
  onLoad: function(options) {  
    // 页面创建时执行的初始化工作  
  },  
  
  startRecord: function() {  
    if (this.data.isRecording) {  
      return;  
    }  
    const recorderManager = wx.getRecorderManager();  
    recorderManager.onStart(() => {  
      console.log('开始录音');  
      this.setData({  
        isRecording: false
      });  
    });  
    recorderManager.onStop((res) => {  
      console.log('录音结束', res.tempFilePath);  
      this.setData({  
        isRecording: false,  
        hasRecorded: true,  
        recordedFilePath: res.tempFilePath  
      });  
    });  
    recorderManager.onError((err) => {  
      console.error('录音失败', err);  
    });  
    recorderManager.start({   
      duration: 30000,
      sampleRate: 44100,  
      numberOfChannels: 1,  
      encodeBitRate: 192000,  
      format: 'mp3', // 目前仅支持 aac/mp3  
      frameSize: 50  
    });  
  },  
  
  stopRecord: function() {  
    // if (!this.data.isRecording) {  
    //   return;  
    // }  
    const recorderManager = wx.getRecorderManager();  
    recorderManager.stop();  
  }  
});