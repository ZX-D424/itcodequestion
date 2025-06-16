import request from '@/utils/request'

// 查询项目菜单列表
export function listMenu(query) {
  return request({
    url: '/www/menu/list',
    method: 'get',
    params: query
  })
}

// 查询项目菜单详细
export function getMenu(id) {
  return request({
    url: '/project/menu/' + id,
    method: 'get'
  })
}

// 新增项目菜单
export function addMenu(data) {
  return request({
    url: '/project/menu',
    method: 'post',
    data: data
  })
}

// 修改项目菜单
export function updateMenu(data) {
  return request({
    url: '/project/menu',
    method: 'put',
    data: data
  })
}

// 删除项目菜单
export function delMenu(id) {
  return request({
    url: '/project/menu/' + id,
    method: 'delete'
  })
}

//获取菜单列表下拉框数据
export function getMenuDataList() {
  return request({
    url: '/www/menu/getMenuDataList',
    method: 'get',
  })
}
