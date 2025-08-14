import request from '@/utils/userRequest'

// 查询用户答题评分列表
export function listQuestions(query) {
  return request({
    url: '/www/questions/list',
    method: 'get',
    params: query
  })
}
// 新增答题评分
export function addQuestions(data) {
  return request({
    url: '/www/questions',
    method: 'post',
    data: data
  })
}