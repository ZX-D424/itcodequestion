<!-- ... existing code ... -->
<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
// 引入 section.js 中的 listSection 方法
import { listSection } from '@/api/section';

const router = useRouter();
const dynamicNavItems = ref([]);

// 获取栏目列表
onMounted(async () => {
  try {
    const response = await listSection(); // 调用 listSection 方法获取数据
    dynamicNavItems.value = response.data.list.map(item => ({
      name: item.name,
      link: `/section/${item.id}` // 根据实际需求生成链接
    }));
  } catch (error) {
    console.error('Failed to fetch nav items:', error);
  }
});

const activeItem = ref(null);
const hoverItem = ref(null);

const isActive = (item) => activeItem.value === item;
const isHover = (item) => hoverItem.value === item;

const active = (item) => {
  activeItem.value = item;
};

const hover = (item) => {
  if (!isActive(item)) {
    hoverItem.value = item;
  }
};

const unhover = (item) => {
  if (!isActive(item)) {
    hoverItem.value = null;
  }
};
</script>
<!-- ... existing code ... -->