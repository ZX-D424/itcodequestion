import request from '@/utils/request'

// 查询练习项目列表
export function listPractice(query) {
  return request({
    url: '/project/practice/list',
    method: 'get',
    params: query
  })
}

// 查询练习项目详细
export function getPractice(id) {
  return request({
    url: '/project/practice/' + id,
    method: 'get'
  })
}

// 新增练习项目
export function addPractice(data) {
  return request({
    url: '/project/practice',
    method: 'post',
    data: data
  })
}

// 修改练习项目
export function updatePractice(data) {
  return request({
    url: '/project/practice',
    method: 'put',
    data: data
  })
}

// 删除练习项目
export function delPractice(id) {
  return request({
    url: '/project/practice/' + id,
    method: 'delete'
  })
}
