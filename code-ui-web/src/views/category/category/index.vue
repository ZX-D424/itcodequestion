<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="父级ID" prop="parentId">
        <!-- <el-input
          v-model="queryParams.parentId"
          placeholder="请输入父级ID"
          clearable
          @keyup.enter="handleQuery"
        /> -->
        <el-select v-model="queryParams.parentName" placeholder="请选择父级" clearable style="width: 200px">
          <el-option
            v-for="category in categoryNameList"
            :key="category.id"
            :label="category.name"
            :value="category.name"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="类目名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入类目名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['category:category:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Sort"
          @click="toggleExpandAll"
        >展开/折叠</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="categoryList"
      row-key="id"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="父级" prop="parentName" />
      <el-table-column label="类目名称" align="center" prop="name" />
      <el-table-column label="排序值" align="center" prop="sort" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['category:category:edit']">修改</el-button>
          <el-button link type="primary" icon="Plus" @click="handleAdd(scope.row)" v-hasPermi="['category:category:add']">新增</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['category:category:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改题库类目对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="categoryRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="父级" prop="parentId">
          <el-tree-select
            v-model="form.parentId"
            :data="categoryOptions"
            :props="{ value: 'id', label: 'name', children: 'children' }"
            value-key="id"
            placeholder="请选择父级"
            check-strictly
          />
        </el-form-item>
        <el-form-item label="类目名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入类目名称" />
        </el-form-item>
        <el-form-item label="排序值" prop="sort">
          <el-input-number v-model="form.sort" controls-position="right" :min="0" />
          <!-- <el-input v-model="form.sort" placeholder="请输入排序值" /> -->
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Category">
import { listCategory, getCategory, delCategory, addCategory, updateCategory, listCategoryNameList } from "@/api/category/category"

const { proxy } = getCurrentInstance()

const categoryList = ref([])
const categoryOptions = ref([])
const categoryNameList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const title = ref("")
const isExpandAll = ref(true)
const refreshTable = ref(true)

const data = reactive({
  form: {},
  queryParams: {
    parentId: null,
    name: null,
    parentName: null
  },
  rules: {
    parentId: [
      { required: true, message: "父级ID不能为空", trigger: "blur" }
    ],
    name: [
      { required: true, message: "类目名称不能为空", trigger: "blur" }
    ],
    sort: [
      { required: true, message: "排序值不能为空", trigger: "blur" }
    ],
    createBy: [
      { required: true, message: "创建者不能为空", trigger: "blur" }
    ],
    createTime: [
      { required: true, message: "创建时间不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询题库类目列表 */
function getList() {
  loading.value = true
  listCategory(queryParams.value).then(response => {
    categoryList.value = proxy.handleTree(response.data, "id", "parentId")
    loading.value = false
  })
}



/** 查询父类题库类目名字列表 */
function getCategoryNameList() {
  listCategoryNameList().then(response => {
    if (response && response.code === 200) {
      const data = response.data || [];
      categoryNameList.value = data.map((item, index) => ({
        id: index + 1, // 假设 id 从 1 开始
        name: item
      }));
    } else {
      categoryNameList.value = [];
    }
  }).catch(error => {
    console.error('获取类目列表失败:', error);
    categoryNameList.value = [];
  });
}


// /** 查询父类题库类目名字列表 */
// function getCategoryNameList() {
//   listCategoryNameList().then(response => {
//     // categoryNameList.value = response.data
//     categoryNameList.value.forEach(data => {
//       data.label = data.name
//       data.value = data.id
//     })
//     // categoryNameList.value.unshift({ id: 0, name: '顶级节点' })
//   })
// }

// /** 查询父类题库类目名字列表 */
// function getCategoryNameList() {
//   listCategoryNameList().then(response => {
//     // 检查返回的数据格式并进行处理
//     if (response && response.data) {
//       // 如果返回的是树形结构，需要展开为平级列表
//       if (Array.isArray(response.data) && response.data.length > 0) {
//         // 判断是否为树形结构
//         if (response.data[0].children !== undefined) {
//           // 展开树形结构为平级列表
//           categoryNameList.value = flattenTree(response.data)
//         } else {
//           // 直接使用平级数据
//           categoryNameList.value = response.data
//         }
//       } else {
//         categoryNameList.value = []
//       }
//     } else {
//       categoryNameList.value = []
//     }
//   }).catch(error => {
//     console.error('获取类目列表失败:', error)
//     categoryNameList.value = []
//   })
// }

/** 查询题库类目下拉树结构 */
function getTreeselect() {
  listCategory().then(response => {
    categoryOptions.value = []
    const data = { id: 0, name: '顶级节点', children: [] }
    data.children = proxy.handleTree(response.data, "id", "parentId")
    categoryOptions.value.push(data)
  })
}
	
// 取消按钮
function cancel() {
  open.value = false
  reset()
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    parentId: null,
    name: null,
    sort: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null
  }
  proxy.resetForm("categoryRef")
}

/** 搜索按钮操作 */
function handleQuery() {
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

/** 新增按钮操作 */
function handleAdd(row) {
  reset()
  getTreeselect()
  if (row != null && row.id) {
    form.value.parentId = row.id
  } else {
    form.value.parentId = 0
  }
  open.value = true
  title.value = "添加题库类目"
}

/** 展开/折叠操作 */
function toggleExpandAll() {
  refreshTable.value = false
  isExpandAll.value = !isExpandAll.value
  nextTick(() => {
    refreshTable.value = true
  })
}

/** 修改按钮操作 */
async function handleUpdate(row) {
  reset()
  await getTreeselect()
  if (row != null) {
    form.value.parentId = row.parentId
  }
  getCategory(row.id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改题库类目"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["categoryRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateCategory(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addCategory(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  proxy.$modal.confirm('是否确认删除题库类目编号为"' + row.id + '"的数据项？').then(function() {
    return delCategory(row.id)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

getList()
getCategoryNameList()
</script>
