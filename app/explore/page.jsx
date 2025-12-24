"use client"

import { useState } from "react"
import Link from "next/link"
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardFooter, CardHeader } from "@/components/ui/card"
import { Badge } from "@/components/ui/badge"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { Input } from "@/components/ui/input"
import { Tabs, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Search, Heart, MessageCircle, BookmarkPlus, TrendingUp, Clock, Filter } from "lucide-react"

const categories = ["All", "Technology", "Design", "Productivity", "Business", "Lifestyle", "Science", "Health"]

const posts = [
  {
    id: 1,
    title: "The Future of Web Development: What to Expect in 2025",
    excerpt:
      "Explore the upcoming trends in web development, from AI-powered tools to new frameworks that will shape how we build.",
    author: { name: "Sarah Chen", avatar: "/woman-developer.png", role: "Senior Developer" },
    category: "Technology",
    readTime: "8 min read",
    likes: 2453,
    comments: 189,
    image: "/futuristic-web-development.png",
    date: "Dec 15, 2025",
  },
  {
    id: 2,
    title: "Building Sustainable Habits for Long-term Success",
    excerpt: "Learn the science-backed strategies for creating habits that stick and transform your productivity.",
    author: { name: "Marcus Johnson", avatar: "/professional-man.png", role: "Life Coach" },
    category: "Productivity",
    readTime: "5 min read",
    likes: 1876,
    comments: 95,
    image: "/productivity-habits.png",
    date: "Dec 14, 2025",
  },
  {
    id: 3,
    title: "The Art of Minimalist Design in Modern Applications",
    excerpt: "Discover how less can be more when it comes to creating beautiful, user-friendly interfaces.",
    author: { name: "Emma Williams", avatar: "/woman-designer.png", role: "UX Designer" },
    category: "Design",
    readTime: "6 min read",
    likes: 1543,
    comments: 67,
    image: "/minimalist-design.png",
    date: "Dec 13, 2025",
  },
  {
    id: 4,
    title: "Understanding Machine Learning: A Beginner's Guide",
    excerpt: "Demystifying AI and machine learning concepts for developers who want to get started in the field.",
    author: { name: "David Park", avatar: "/asian-male-data-scientist.jpg", role: "ML Engineer" },
    category: "Technology",
    readTime: "12 min read",
    likes: 3241,
    comments: 234,
    image: "/ml-neural-network-visualization.png",
    date: "Dec 12, 2025",
  },
  {
    id: 5,
    title: "Remote Work: Building a Productive Home Office",
    excerpt:
      "Essential tips and setups for creating an environment that boosts your productivity while working from home.",
    author: { name: "Lisa Thompson", avatar: "/professional-interior-designer.png", role: "Interior Designer" },
    category: "Lifestyle",
    readTime: "7 min read",
    likes: 892,
    comments: 45,
    image: "/modern-home-office.png",
    date: "Dec 11, 2025",
  },
  {
    id: 6,
    title: "The Psychology of Color in Brand Design",
    excerpt: "How colors influence perception and emotion in branding, and how to use them effectively.",
    author: { name: "Michael Ross", avatar: "/creative-director-male.jpg", role: "Creative Director" },
    category: "Design",
    readTime: "9 min read",
    likes: 1234,
    comments: 78,
    image: "/color-psychology-brand-design.jpg",
    date: "Dec 10, 2025",
  },
]

export default function ExplorePage() {
  const [searchQuery, setSearchQuery] = useState("")
  const [selectedCategory, setSelectedCategory] = useState("All")
  const [sortBy, setSortBy] = useState("trending")

  const filteredPosts = posts.filter((post) => {
    const matchesSearch =
      post.title.toLowerCase().includes(searchQuery.toLowerCase()) ||
      post.excerpt.toLowerCase().includes(searchQuery.toLowerCase())
    const matchesCategory = selectedCategory === "All" || post.category === selectedCategory
    return matchesSearch && matchesCategory
  })

  return (
    <div className="min-h-screen">
      {/* Header */}
      <div className="border-b border-border bg-card">
        <div className="mx-auto max-w-7xl px-4 py-8 sm:px-6 lg:px-8">
          <h1 className="text-3xl font-bold text-foreground sm:text-4xl">Explore Stories</h1>
          <p className="mt-2 text-muted-foreground">Discover articles, insights, and ideas from our community</p>

          {/* Search */}
          <div className="mt-6 flex flex-col gap-4 sm:flex-row">
            <div className="relative flex-1">
              <Search className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-muted-foreground" />
              <Input
                placeholder="Search articles..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="pl-10"
              />
            </div>
            <div className="flex gap-2">
              <Tabs value={sortBy} onValueChange={setSortBy}>
                <TabsList>
                  <TabsTrigger value="trending" className="gap-2">
                    <TrendingUp className="h-4 w-4" /> Trending
                  </TabsTrigger>
                  <TabsTrigger value="latest" className="gap-2">
                    <Clock className="h-4 w-4" /> Latest
                  </TabsTrigger>
                </TabsList>
              </Tabs>
              <Button variant="outline" size="icon" className="bg-transparent">
                <Filter className="h-4 w-4" />
              </Button>
            </div>
          </div>

          {/* Categories */}
          <div className="mt-6 flex flex-wrap gap-2">
            {categories.map((category) => (
              <Button
                key={category}
                variant={selectedCategory === category ? "default" : "outline"}
                size="sm"
                onClick={() => setSelectedCategory(category)}
                className={selectedCategory === category ? "" : "bg-transparent"}
              >
                {category}
              </Button>
            ))}
          </div>
        </div>
      </div>

      {/* Posts Grid */}
      <div className="mx-auto max-w-7xl px-4 py-8 sm:px-6 lg:px-8">
        <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-3">
          {filteredPosts.map((post) => (
            <Card key={post.id} className="group flex flex-col overflow-hidden border-border">
              <div className="aspect-[16/10] overflow-hidden">
                <img
                  src={post.image || "/placeholder.svg"}
                  alt={post.title}
                  className="h-full w-full object-cover transition-transform duration-300 group-hover:scale-105"
                />
              </div>
              <CardHeader className="pb-2">
                <div className="flex items-center justify-between">
                  <Badge variant="secondary">{post.category}</Badge>
                  <span className="text-xs text-muted-foreground">{post.date}</span>
                </div>
                <h3 className="mt-2 line-clamp-2 text-lg font-semibold text-foreground group-hover:text-primary transition-colors">
                  <Link href={`/post/${post.id}`}>{post.title}</Link>
                </h3>
              </CardHeader>
              <CardContent className="flex-1 pb-4">
                <p className="line-clamp-2 text-sm text-muted-foreground">{post.excerpt}</p>
              </CardContent>
              <CardFooter className="flex items-center justify-between border-t border-border pt-4">
                <div className="flex items-center gap-2">
                  <Avatar className="h-8 w-8">
                    <AvatarImage src={post.author.avatar || "/placeholder.svg"} />
                    <AvatarFallback>{post.author.name[0]}</AvatarFallback>
                  </Avatar>
                  <div>
                    <p className="text-sm font-medium text-foreground">{post.author.name}</p>
                    <p className="text-xs text-muted-foreground">{post.readTime}</p>
                  </div>
                </div>
                <div className="flex items-center gap-3">
                  <button className="flex items-center gap-1 text-muted-foreground hover:text-primary transition-colors">
                    <Heart className="h-4 w-4" />
                    <span className="text-xs">{post.likes}</span>
                  </button>
                  <button className="flex items-center gap-1 text-muted-foreground hover:text-primary transition-colors">
                    <MessageCircle className="h-4 w-4" />
                    <span className="text-xs">{post.comments}</span>
                  </button>
                  <button className="text-muted-foreground hover:text-primary transition-colors">
                    <BookmarkPlus className="h-4 w-4" />
                  </button>
                </div>
              </CardFooter>
            </Card>
          ))}
        </div>

        {filteredPosts.length === 0 && (
          <div className="py-20 text-center">
            <p className="text-muted-foreground">No articles found matching your criteria.</p>
            <Button
              variant="link"
              onClick={() => {
                setSearchQuery("")
                setSelectedCategory("All")
              }}
            >
              Clear filters
            </Button>
          </div>
        )}

        {/* Load More */}
        {filteredPosts.length > 0 && (
          <div className="mt-8 text-center">
            <Button variant="outline" className="bg-transparent">
              Load More Articles
            </Button>
          </div>
        )}
      </div>
    </div>
  )
}
