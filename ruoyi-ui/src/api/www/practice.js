import request from '@/utils/userRequest'

// 查询练习项目列表
export function getPracticeDataList(query) {
  return request({
    url: '/www/practice/getPracticeDataList',
    method: 'get',
    params: query
  })
}
