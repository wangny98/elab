
function printEcharts(record){
	 
  var myChart1 = echarts.getInstanceByDom(document.getElementById(record.chart1));
  var option1 = {
          series: [
              {
                  name: '业务指标',
                  type: 'gauge',
                  min: 0,
                  max: 400,
                  detail: {
                    formatter:'{value} cm/min',
                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                        fontWeight: 'bolder',
                        fontSize: 15
                    }
                  },
                  data: [{value:record.speed, name: '速度'}],
                  axisLine:{
                    lineStyle:{
                      width:5,
                      color:[[0.75, '#0000FF '], [1, '#c23531']]
                    }
                  },
                  axisTick: {            // 坐标轴小标记
                      length: 10,        // 属性length控制线长
                      lineStyle: {       // 属性lineStyle控制线条样式
                          color: 'auto'
                      }
                  },
                  splitNumber: 8,
                  splitLine: {           // 分隔线
                      length: 15,         // 属性length控制线长
                      lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                          color: 'auto'
                      }
                  },
              }
          ]
      };
  if(myChart1){
	  myChart1.getOption().series[0].data[0].value = record.speed;
  }else{
	  myChart1 =  echarts.init(document.getElementById(record.chart1));
  }
  myChart1.setOption(option1,true);
  /******************************************************************************/
  var myChart2 = echarts.getInstanceByDom(document.getElementById(record.chart2));
  var option2 = {
          series: [
              {
                  name: '业务指标',
                  type: 'gauge',
                  min: 0,
                  max: 100,
                  detail: {
                    formatter:'{value} L/min',
                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                        fontWeight: 'bolder',
                        fontSize: 15
                    }
                  },
                  data: [{value: record.flow, name: '流量'}],
                  axisLine:{
                    lineStyle:{
                      width:5,
                      color:[[0.75, '#0000FF '], [1, '#c23531']]
                    }
                  },
                  axisTick: {            // 坐标轴小标记
                      length: 10,        // 属性length控制线长
                      lineStyle: {       // 属性lineStyle控制线条样式
                          color: 'auto'
                      }
                  },
                  splitNumber: 2,
                  splitLine: {           // 分隔线
                      length: 15,         // 属性length控制线长
                      lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                          color: 'auto'
                      }
                  },
              }
          ]
      };
  if(myChart2){
	  myChart2.getOption().series[0].data[0].value = record.flow;
  }else{
	  myChart2 = echarts.init(document.getElementById(record.chart2));
  }
  myChart2.setOption(option2,true);
  /****************************************************************************/
  var myChart3 = echarts.getInstanceByDom(document.getElementById(record.chart3));
  var option3 = {
          series: [
              {
                  name: '业务指标',
                  type: 'gauge',
                  min: 0,
                  max: 200,
                  detail: {
                    formatter:'{value} A',
                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                        fontWeight: 'bolder',
                        fontSize: 15
                    }
                  },
                  data: [{value: record.outsidePower, name: '电流'}],
                  axisLine:{
                    lineStyle:{
                      width:5,
                      color:[[0.75, '#0000FF '], [1, '#c23531']]
                    }
                  },
                  axisTick: {            // 坐标轴小标记
                      length: 10,        // 属性length控制线长
                      lineStyle: {       // 属性lineStyle控制线条样式
                          color: 'auto'
                      }
                  },
                  splitNumber: 4,
                  splitLine: {           // 分隔线
                      length: 15,         // 属性length控制线长
                      lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                          color: 'auto'
                      }
                  },
              }
          ]
      };
  if(myChart3){
	  myChart3.getOption().series[0].data[0].value = record.maxOutsidePower;
  }else{
	  myChart3 = echarts.init(document.getElementById(record.chart3));
  }
  myChart3.setOption(option3,true);
  /****************************************************************************/
  
  var myChart4 = echarts.getInstanceByDom(document.getElementById(record.chart4));
  var option4 = {
          series: [
              {
                  name: '业务指标',
                  type: 'gauge',
                  min: 0,
                  max: 20,
                  detail: {
                    formatter:'{value} %',
                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                        fontWeight: 'bolder',
                        fontSize: 15
                    }
                  },
                  data: [{value: record.lean, name: '倾角'}],
                  axisLine:{
                    lineStyle:{
                      width:5,
                      color:[[0.75, '#0000FF '], [1, '#c23531']]
                    }
                  },
                  axisTick: {            // 坐标轴小标记
                      length: 10,        // 属性length控制线长
                      lineStyle: {       // 属性lineStyle控制线条样式
                          color: 'auto'
                      }
                  },
                  splitNumber: 4,
                  splitLine: {           // 分隔线
                      length: 15,         // 属性length控制线长
                      lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                          color: 'auto'
                      }
                  },
              }
          ]
      };
  if(myChart4){
	  myChart4.getOption().series[0].data[0].value = record.lean;
  }else{
	  myChart4 = echarts.init(document.getElementById(record.chart4));
  }
  myChart4.setOption(option4,true);
  
}
