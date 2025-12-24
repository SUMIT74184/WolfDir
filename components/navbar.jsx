"use client"

import { useState, useEffect } from "react"
import Link from "next/link"
import Image from "next/image"
import { useTheme } from "next-themes"
import { Button } from "@/components/ui/button"
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger } from "@/components/ui/dropdown-menu"
import { Sun, Moon, Menu, X, Search, PenSquare, ChevronDown } from "lucide-react"

export function Navbar() {
  // use resolvedTheme so we always get "light" or "dark" (considers system)
  const { theme, setTheme, resolvedTheme } = useTheme()

  const [mobileMenuOpen, setMobileMenuOpen] = useState(false)
  const [mounted, setMounted] = useState(false)

  useEffect(() => {
    setMounted(true)
  }, [])

  const navigation = [
    { name: "Home", href: "/" },
    { name: "Explore", href: "/explore" },
    { name: "Communities", href: "/communities" },
    { name: "Pricing", href: "/pricing" },
  ]

  // resolvedTheme is safe to use for toggling (gives "light" | "dark")
  // avoid hydration flicker by using mounted to decide which theme to show
  const activeTheme = mounted ? resolvedTheme : theme
  const toggleTheme = () => {
    const next = (activeTheme === "dark") ? "light" : "dark"
    setTheme(next)
  }

  return (
    <nav className="sticky top-0 z-50 border-b border-border bg-background/80 backdrop-blur-lg">
      <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
        <div className="flex h-16 items-center justify-between">
          {/* Logo */}
          <div className="flex items-center gap-8">
            <Link href="/" className="flex items-center gap-2">
              <div className="relative flex h-9 w-9 items-center justify-center rounded-lg bg-primary">
                <Image
                  src="/Logo of a Wolf Head.png"
                  alt="Wolf Logo"
                  fill
                  className="object-contain p-1"
                  priority
                />
              </div>
              <span className="text-xl font-bold text-foreground">WolfDire</span>
            </Link>

            {/* Desktop Navigation */}
            <div className="hidden md:flex md:items-center md:gap-1">
              {navigation.map((item) => (
                <Link
                  key={item.name}
                  href={item.href}
                  className="rounded-lg px-4 py-2 text-sm font-medium text-muted-foreground transition-colors hover:bg-secondary hover:text-foreground"
                >
                  {item.name}
                </Link>
              ))}
              <DropdownMenu>
                <DropdownMenuTrigger asChild>
                  <Button variant="ghost" className="gap-1 text-muted-foreground">
                    More <ChevronDown className="h-4 w-4" />
                  </Button>
                </DropdownMenuTrigger>
                <DropdownMenuContent align="start">
                  <DropdownMenuItem asChild>
                    <Link href="/about">About</Link>
                  </DropdownMenuItem>
                  <DropdownMenuItem asChild>
                    <Link href="/careers">Careers</Link>
                  </DropdownMenuItem>
                  <DropdownMenuItem asChild>
                    <Link href="/contact">Contact</Link>
                  </DropdownMenuItem>
                </DropdownMenuContent>
              </DropdownMenu>
            </div>
          </div>

          {/* Right side */}
          <div className="flex items-center gap-2">
            {/* Search */}
            <Button variant="ghost" size="icon" className="hidden sm:flex">
              <Search className="h-5 w-5" />
            </Button>

            {/* Theme toggle */}
            <Button variant="ghost" size="icon" onClick={toggleTheme}>
              {mounted ? (activeTheme === "dark" ? <Sun className="h-5 w-5" /> : <Moon className="h-5 w-5" />) : <Moon className="h-5 w-5" />}
            </Button>

            {/* Write button */}
            <Button asChild variant="ghost" className="hidden sm:flex gap-2">
              <Link href="/write">
                <PenSquare className="h-4 w-4" />
                Write
              </Link>
            </Button>

            {/* Auth buttons */}
            <div className="hidden md:flex md:items-center md:gap-2">
              <Button asChild variant="ghost">
                <Link href="/login">Sign In</Link>
              </Button>
              <Button asChild>
                <Link href="/signup">Get Started</Link>
              </Button>
            </div>

            {/* Mobile menu button */}
            <Button
              variant="ghost"
              size="icon"
              className="md:hidden"
              onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
            >
              {mobileMenuOpen ? <X className="h-5 w-5" /> : <Menu className="h-5 w-5" />}
            </Button>
          </div>
        </div>

        {/* Mobile menu */}
        {mobileMenuOpen && (
          <div className="border-t border-border py-4 md:hidden">
            <div className="flex flex-col gap-2">
              {navigation.map((item) => (
                <Link
                  key={item.name}
                  href={item.href}
                  className="rounded-lg px-4 py-2 text-sm font-medium text-muted-foreground transition-colors hover:bg-secondary hover:text-foreground"
                  onClick={() => setMobileMenuOpen(false)}
                >
                  {item.name}
                </Link>
              ))}
              <hr className="my-2 border-border" />
              <Link
                href="/write"
                className="flex items-center gap-2 rounded-lg px-4 py-2 text-sm font-medium text-muted-foreground transition-colors hover:bg-secondary hover:text-foreground"
                onClick={() => setMobileMenuOpen(false)}
              >
                <PenSquare className="h-4 w-4" />
                Write
              </Link>
              <hr className="my-2 border-border" />
              <div className="flex gap-2 px-4">
                <Button asChild variant="outline" className="flex-1 bg-transparent">
                  <Link href="/login">Sign In</Link>
                </Button>
                <Button asChild className="flex-1">
                  <Link href="/signup">Get Started</Link>
                </Button>
              </div>
            </div>
          </div>
        )}
      </div>
    </nav>
  )
}
