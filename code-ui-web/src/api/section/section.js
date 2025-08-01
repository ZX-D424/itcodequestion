import request from '@/utils/request'

// 查询栏目列表
export function listSection(query) {
  return request({
    url: '/section/section/list',
    method: 'get',
    params: query
  })
}

// 查询栏目详细
export function getSection(id) {
  return request({
    url: '/section/section/' + id,
    method: 'get'
  })
}

// 新增栏目
export function addSection(data) {
  return request({
    url: '/section/section',
    method: 'post',
    data: data
  })
}

// 修改栏目
export function updateSection(data) {
  return request({
    url: '/section/section',
    method: 'put',
    data: data
  })
}

// 删除栏目
export function delSection(id) {
  return request({
    url: '/section/section/' + id,
    method: 'delete'
  })
}
