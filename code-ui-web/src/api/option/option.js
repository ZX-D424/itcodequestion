import request from '@/utils/request'

// 查询题目选项（支持单选/多选/判断题，题目删除需级联删除选项）列表
export function listOption(query) {
  return request({
    url: '/option/option/list',
    method: 'get',
    params: query
  })
}

// 查询题目选项（支持单选/多选/判断题，题目删除需级联删除选项）详细
export function getOption(id) {
  return request({
    url: '/option/option/' + id,
    method: 'get'
  })
}

// 新增题目选项（支持单选/多选/判断题，题目删除需级联删除选项）
export function addOption(data) {
  return request({
    url: '/option/option',
    method: 'post',
    data: data
  })
}

// 修改题目选项（支持单选/多选/判断题，题目删除需级联删除选项）
export function updateOption(data) {
  return request({
    url: '/option/option',
    method: 'put',
    data: data
  })
}

// 删除题目选项（支持单选/多选/判断题，题目删除需级联删除选项）
export function delOption(id) {
  return request({
    url: '/option/option/' + id,
    method: 'delete'
  })
}
