import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import ListOfCourses from "./Components/CoursesList";
import CourseFiles from "./Components/CourseFiles";
import { Routes, Route, BrowserRouter } from "react-router-dom";

const queryClient = new QueryClient();

function App() {
  return (
      <QueryClientProvider client={queryClient}>
        <BrowserRouter>
        <Routes>
          <Route path="/" element={<ListOfCourses />} />
          <Route path="/course-files/:courseId" element={<CourseFiles />} />
        </Routes>
       </BrowserRouter>
      </QueryClientProvider>
  );
}
export default App;
