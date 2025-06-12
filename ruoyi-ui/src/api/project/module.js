import request from '@/utils/request'

// 查询项目模块列表
export function listModule(query) {
  return request({
    url: '/project/module/list',
    method: 'get',
    params: query
  })
}

// 查询项目模块详细
export function getModule(id) {
  return request({
    url: '/project/module/' + id,
    method: 'get'
  })
}

// 新增项目模块
export function addModule(data) {
  return request({
    url: '/project/module',
    method: 'post',
    data: data
  })
}

// 修改项目模块
export function updateModule(data) {
  return request({
    url: '/project/module',
    method: 'put',
    data: data
  })
}

// 删除项目模块
export function delModule(id) {
  return request({
    url: '/project/module/' + id,
    method: 'delete'
  })
}
