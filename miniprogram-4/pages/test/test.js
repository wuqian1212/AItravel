// const recorderManager = wx.getRecorderManager()
// const plugin = requirePlugin("WechatSI")

// Page({
//   data: {
//     recognizedText: '',
//     isRecording: false
//   },

//   onLoad: function() {
//     this.initRecorderManager()
//   },

//   initRecorderManager: function() {
//     recorderManager.onStart(() => {
//       console.log('Recorder start')
//       this.setData({ isRecording: true })
//     })

//     recorderManager.onStop((res) => {
//       console.log('Recorder stop', res)
//       this.setData({ isRecording: false })
      
//       // 开始识别
//       plugin.textToSpeech({
//         lang: "zh_CN",
//         content: "开始识别"
//       })
      
//       const { tempFilePath } = res
//       wx.uploadFile({
//         url: 'YOUR_SERVER_URL', // 替换为你的服务器地址
//         filePath: tempFilePath,
//         name: 'file',
//         success: (res) => {
//           const data = JSON.parse(res.data)
//           this.setData({
//             recognizedText: data.result
//           })
//         },
//         fail: (res) => {
//           console.error('Upload failed', res)
//           wx.showToast({
//             title: '识别失败',
//             icon: 'none'
//           })
//         }
//       })
//     })

//     recorderManager.onError((res) => {
//       console.log('Recorder error:', res)
//       wx.showToast({
//         title: '录音出错',
//         icon: 'none'
//       })
//       this.setData({ isRecording: false })
//     })
//   },

//   toggleRecording: function() {
//     if (this.data.isRecording) {
//       this.stopRecording()
//     } else {
//       this.startRecording()
//     }
//   },

//   startRecording: function() {
//     const options = {
//       duration: 60000,
//       sampleRate: 16000,
//       numberOfChannels: 1,
//       encodeBitRate: 96000,
//       format: 'mp3',
//       frameSize: 50
//     }

//     recorderManager.start(options)
//   },

//   stopRecording: function() {
//     recorderManager.stop()
//   }
// })
