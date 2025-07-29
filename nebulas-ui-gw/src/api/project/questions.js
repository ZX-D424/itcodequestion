import request from '@/utils/request'

// 查询答题评分列表
export function listQuestions(query) {
  return request({
    url: '/project/questions/list',
    method: 'get',
    params: query
  })
}

// 查询答题评分详细
export function getQuestions(id) {
  return request({
    url: '/project/questions/' + id,
    method: 'get'
  })
}

// 新增答题评分
export function addQuestions(data) {
  return request({
    url: '/project/questions',
    method: 'post',
    data: data
  })
}

// 修改答题评分
export function updateQuestions(data) {
  return request({
    url: '/project/questions',
    method: 'put',
    data: data
  })
}

// 删除答题评分
export function delQuestions(id) {
  return request({
    url: '/project/questions/' + id,
    method: 'delete'
  })
}
